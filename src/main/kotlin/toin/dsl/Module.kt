package toin.dsl

import toin.Module

fun module(moduleDeclaration: Module.() -> Unit): Module {
    val module = Module()
    moduleDeclaration(module)
    return module
}