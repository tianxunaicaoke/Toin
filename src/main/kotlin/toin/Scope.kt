package toin

import kotlin.reflect.KClass
import toin.instance.InstanceContext

class Scope(
    val scopeQualifier: String,
    val id: String,
    val isRoot: Boolean = false,
    internal val _toin: Toin
) {
    private val linkedScopes: ArrayList<Scope> = arrayListOf()

    internal inline fun <reified T : Any> get(
        qualifier: String? = null,
        parameters: ParametersHolder? = null
    ): T {
        return get(T::class,qualifier,parameters)
    }

    internal fun <T> get(
        clazz: KClass<*>,
        qualifier: String? = null,
        parameters: ParametersHolder? = null
    ): T {
        val instanceContext = InstanceContext(_toin, this, parameters)
        return resolveValue(qualifier, clazz, instanceContext, parameters)
    }

    private fun <T> resolveValue(
        qualifier: String?,
        clazz: KClass<*>,
        instanceContext: InstanceContext,
        parameterDef: ParametersHolder?
    ) = _toin.instanceRegistry.resolveInstance<T>(qualifier, clazz, scopeQualifier, instanceContext)
        ?: findInOtherScope<T>(clazz, qualifier, parameterDef) ?:
    run {
        throw IllegalAccessError()
    }

    fun linkTo(vararg scopes: Scope) {
        if (!isRoot) {
            linkedScopes.addAll(scopes)
        } else {
            error("Can't add scope link to a root scope")
        }
    }

    private fun<T> findInOtherScope(
                                    clazz: KClass<*>,
                                    qualifier: String?,
                                    parameters: ParametersHolder?
    ): T? {
        var instance: T? = null
        for (scope in linkedScopes) {
            instance = scope.get(
                clazz,
                qualifier,
                parameters
            ) as T
            if (instance != null) break
        }
        return instance
    }
}