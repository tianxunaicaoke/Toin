package toin.instance

import toin.BeanDefinition
import toin.ParametersHolder

abstract class InstanceFactory<T>(private val beanDefinition: BeanDefinition<T>) {
    abstract fun get(context: InstanceContext): T
    abstract fun isCreated(context: InstanceContext? = null): Boolean
    open fun create(context: InstanceContext): T {
        return beanDefinition.definition.invoke(
            context.scope,
            context.parameters ?: ParametersHolder()
        )
    }
}