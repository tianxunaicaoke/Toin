package toin.register

import toin.instance.InstanceContext
import toin.instance.InstanceFactory
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import toin.Module
import toin.indexKey

class InstanceRegistry {
    private val _instances = ConcurrentHashMap<String, InstanceFactory<*>>()
    val instances: Map<String, InstanceFactory<*>>
        get() = _instances

    internal fun <T> resolveInstance(
        qualifier: String?,
        clazz: KClass<*>,
        scopeQualifier: String,
        instanceContext: InstanceContext
    ): T? {
        val indexKey = indexKey(clazz, qualifier, scopeQualifier)
        return _instances[indexKey]?.get(instanceContext) as? T
    }

    internal fun loadModules(modules: List<Module>) {
        modules.forEach { module ->
            loadModule(module)
        }
    }

    private fun loadModule(module: Module) {
        module.mappings.forEach { (mapping, factory) ->
            saveMapping(mapping, factory)
        }
    }

    fun saveMapping(
        mapping: String,
        factory: InstanceFactory<*>
    ) {
        _instances[mapping] = factory
    }
}