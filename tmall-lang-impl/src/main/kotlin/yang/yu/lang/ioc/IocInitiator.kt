package yang.yu.lang.ioc

import jakarta.inject.Named
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import yang.yu.lang.IoC

@Named
class IocInitiator : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        IoC.provider = SpringInstanceProvider(applicationContext)
    }
}
