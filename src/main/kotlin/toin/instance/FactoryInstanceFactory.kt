package toin.instance

import toin.BeanDefinition

class FactoryInstanceFactory<T>(beanDefinition: BeanDefinition<T>) :
    InstanceFactory<T>(beanDefinition) {

    override fun isCreated(context: InstanceContext?): Boolean = false

    override fun get(context: InstanceContext): T {
        return create(context)
    }
}