package toin

import java.lang.RuntimeException
import toin.register.InstanceRegistry
import toin.register.ScopeRegister

class Toin {
    //Scope register
    val scopeRegistry = ScopeRegister(this)

    //Instance register
    val instanceRegistry = InstanceRegistry()

    fun loadModules(modules: List<Module>) {
        instanceRegistry.loadModules(modules)
        scopeRegistry.loadScopes(modules)
    }

    fun getScope(scopeId: String): Scope {
        return scopeRegistry.getScopeOrNull(scopeId)
            ?: throw RuntimeException("No scope found for id '$scopeId'")
    }

    fun createScope(scopeId: String, qualifier: String, source: Any? = null): Scope {
        return scopeRegistry.createScope(scopeId, qualifier, source)
    }
}