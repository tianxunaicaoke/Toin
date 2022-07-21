package toin.register

import java.util.concurrent.ConcurrentHashMap
import toin.Scope
import toin.Toin
import toin.Module

class ScopeRegister(private val _toin: Toin) {
    private val _scopeDefinitions = HashSet<String>()
    val scopeDefinitions: Set<String>
        get() = _scopeDefinitions
    private val _scopes = ConcurrentHashMap<String, Scope>()

    val rootScope = Scope("root", "ROOT_SCOPE_ID", isRoot = true, _toin = _toin)

    @PublishedApi
    internal fun createScope(scopeId: String, qualifier: String, source: Any? = null): Scope {
        if (!_scopeDefinitions.contains(qualifier)) {
            _scopeDefinitions.add(qualifier)
        }
        val scope = Scope(qualifier, scopeId, _toin = _toin)
        _scopes[scopeId] = scope
        scope.linkTo(rootScope)
        return scope
    }

    fun loadScopes(modules: List<Module>) {
        modules.forEach {
            loadModule(it)
        }
    }

    private fun loadModule(module: Module) {
        _scopeDefinitions.addAll(module.scopes)
    }

    @PublishedApi
    internal fun getScopeOrNull(scopeId: String): Scope? {
        return _scopes[scopeId]
    }
}