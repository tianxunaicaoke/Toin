package toin.instance

import toin.ParametersHolder
import toin.Scope
import toin.Toin

class InstanceContext(
    val toin: Toin,
    val scope: Scope,
    val parameters: ParametersHolder? = null
)