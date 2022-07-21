package toin.test

import A
import B
import C
import toin.Toin
import toin.dsl.module

class ScopeOne

val m = module {
    scope<ScopeOne> {
        scoped { C() }
        scoped {
            A(get(), get())
        }
    }
    factory {
        B()
    }
}

fun main() {
  val toin = Toin()
    toin.loadModules(listOf(m))

    val scope = toin.createScope("ScopeOne", "ScopeOne")
    (1..3).forEach {
        println(scope.get<A>().print())
    }
}