package toin.instance

import java.lang.IllegalArgumentException
import toin.BeanDefinition

class ScopedInstanceFactory<T>(beanDefinition: BeanDefinition<T>) : InstanceFactory<T>(beanDefinition) {
    private var values = hashMapOf<String, T>()
    override fun isCreated(context: InstanceContext?): Boolean = (values[context?.scope?.id] != null)

    override fun create(context: InstanceContext): T {
        return values[context.scope.id] ?: let {
            super.create(context)
        }
    }

    override fun get(context: InstanceContext): T {
        if (!isCreated(context)) {
            values[context.scope.id] = super.create(context)
        }
        return values[context.scope.id]?:run{
            throw IllegalArgumentException()
        }
    }
}