package toin.test

import org.koin.dsl.module
import A
import B
import C
import org.koin.dsl.koinApplication

val module = module {
    scope<ScopeOne> {
        scoped { B() }
        scoped { C() }
    }
    factory {
        A(get(), get())
    }
}

fun main() {
    val koinApp = koinApplication {
        modules(module)
    }
    val scope = koinApp.koin.createScope<ScopeOne>()
    print(koinApp.koin.get<A>().print())
}