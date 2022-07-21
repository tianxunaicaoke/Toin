package toin

import toin.dsl.ScopeDSL
import toin.instance.FactoryInstanceFactory
import toin.instance.InstanceFactory

class Module {

    @PublishedApi
    internal val scopes = hashSetOf<String>()

    @PublishedApi
    internal val mappings = hashMapOf<String, InstanceFactory<*>>()


    inline fun <reified T> scope(scopeSet: ScopeDSL.() -> Unit) {
        val scopeName = T::class.simpleName ?: ""
        ScopeDSL(scopeName, this).apply(scopeSet)
        scopes.add(scopeName)
    }

    @PublishedApi
    internal inline fun <reified T> factory(
        qualifier: String? = null,
        scopeQualifier: String = "root",
        noinline definition:Scope.(ParametersHolder) -> T
    ): Pair<Module, InstanceFactory<T>> {
        val def = createDefinition(Kind.Factory, qualifier, definition, scopeQualifier = scopeQualifier)
        val mapping = indexKey(def.primaryType, qualifier, scopeQualifier)
        val instanceFactory = FactoryInstanceFactory(def)
        saveMapping(mapping, instanceFactory)
        return Pair(this, instanceFactory)
    }

    @PublishedApi
    internal fun saveMapping(mapping: String, factory: InstanceFactory<*>) {
        mappings[mapping] = factory
    }
}