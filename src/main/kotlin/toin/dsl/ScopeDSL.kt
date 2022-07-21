package toin.dsl

import toin.Kind
import toin.Module
import toin.ParametersHolder
import toin.Scope
import toin.createDefinition
import toin.indexKey
import toin.instance.InstanceFactory
import toin.instance.ScopedInstanceFactory

class ScopeDSL(val scopeQualifier: String, val module: Module) {

    inline fun <reified T> scoped(
        qualifier: String? = null,
        noinline scope: Scope.(ParametersHolder) -> T
    ): Pair<Module, InstanceFactory<T>> {
        val def = createDefinition(Kind.Scoped, qualifier, scope, scopeQualifier = scopeQualifier)
        val mapping = indexKey(def.primaryType, qualifier, scopeQualifier)
        val instanceFactory = ScopedInstanceFactory(def)
        module.saveMapping(mapping, instanceFactory)
        return Pair(module, instanceFactory)
    }
}
