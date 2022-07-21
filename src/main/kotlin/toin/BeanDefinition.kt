package toin

import kotlin.reflect.KClass

data class BeanDefinition<T>(
    val scopeQualifier: String,
    val primaryType: KClass<*>,
    val qualifier: String? = null,
    val definition: Scope.(ParametersHolder) -> T,
    val kind: Kind
)

fun indexKey(clazz: KClass<*>, typeQualifier: String?, scopeQualifier: String): String {
    val tq = typeQualifier ?: ""
    return "${clazz.qualifiedName}:$tq:$scopeQualifier"
}

enum class Kind {
    Singleton, Factory, Scoped
}

inline fun <reified T> createDefinition(
    kind: Kind = Kind.Singleton,
    qualifier: String? = null,
    noinline definition: Scope.(ParametersHolder) -> T,
    scopeQualifier: String
): BeanDefinition<T> {
    return BeanDefinition(
        scopeQualifier,
        T::class,
        qualifier,
        definition,
        kind
    )
}