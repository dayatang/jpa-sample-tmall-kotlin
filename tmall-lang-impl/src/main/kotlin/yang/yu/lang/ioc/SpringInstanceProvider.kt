package yang.yu.lang.ioc

import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import yang.yu.lang.InstanceProvider
import yang.yu.lang.IocInstanceNotFoundException
import yang.yu.lang.IocInstanceNotUniqueException

/**
 * 实例提供者接口的Spring实现。
 * SpringProvider内部通过Spring IoC的ApplicationContext实现对象创建。
 *
 * @author yyang ([gdyangyu@gmail.com](mailto:gdyangyu@gmail.com))
 */
class SpringInstanceProvider : InstanceProvider {

    private var applicationContext: ApplicationContext

    /**
     * 以一批spring配置文件的路径初始化spring实例提供者。
     *
     * @param locations spring配置文件的路径的集合。spring将从类路径开始获取这批资源文件。
     */
    constructor(vararg locations: String) {
        applicationContext = ClassPathXmlApplicationContext(*locations)
    }

    /**
     * 从ApplicationContext生成SpringProvider
     *
     * @param applicationContext
     */
    constructor(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    /**
     * 根据一批Spring配置文件初始化spring实例提供者。
     *
     * @param annotatedClasses
     */
    constructor(vararg annotatedClasses: Class<*>?) {
        applicationContext = AnnotationConfigApplicationContext(*annotatedClasses)
    }

    /**
     * 根据类型获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。如果找不到该类型的实例则返回null。
     * 如果有部署了多个类型为T的Bean则抛出NoUniqueBeanDefinitionException异常。
     *
     * @param <T>      类型参数
     * @param beanType 实例的类型
     * @return 指定类型的实例。
    </T> */
    override fun <T> getInstance(beanType: Class<T>): T {
        try {
            return applicationContext.getBean(beanType)
        } catch (e: NoUniqueBeanDefinitionException) {
            throw IocInstanceNotUniqueException(e)
        } catch (e: NoSuchBeanDefinitionException) {
            throw IocInstanceNotFoundException()
        }
    }

    /**
     * 根据类型和Bean id获取对象实例。如果找不到该类型的实例则返回null。
     * @param <T>      类型参数
     * @param beanName 实现类在容器中配置的名字
     * @param beanType 实例的类型
     * @return 指定类型的实例。
    </T> */
    override fun <T> getInstance(beanType: Class<T>, beanName: String): T {
        try {
            return applicationContext.getBean(beanName, beanType) as T
        } catch (e: NoUniqueBeanDefinitionException) {
            throw IocInstanceNotUniqueException(e)
        } catch (e: NoSuchBeanDefinitionException) {
            throw IocInstanceNotFoundException()
        }
    }


    /**
     * 根据类型和Annotation获取对象实例。如果找不到该类型的实例则返回null。
     * 假如有两个类MyService1和MyService2都实现了接口Service，其中MyService2标记为
     * TheAnnotation，那么getInstance(Service.class, TheAnnotation.class)将返回
     * MyService2的实例。
     *
     * @param <T>            类型参数
     * @param beanType       实例的类型
     * @param annotationType 实现类的annotation类型
     * @return 指定类型的实例。
    </T> */
    override fun <T> getInstance(beanType: Class<T>, annotationType: Class<out Annotation>): T {
        val results = applicationContext.getBeansOfType(beanType)
        val resultsWithAnnotation: MutableList<T> = ArrayList()
        for ((key, value) in results) {
            if (applicationContext.findAnnotationOnBean(key, annotationType) != null) {
                resultsWithAnnotation.add(value)
            }
        }
        if (resultsWithAnnotation.size == 1) {
            return resultsWithAnnotation.first()
        }
        if (resultsWithAnnotation.isEmpty()) {
            throw IocInstanceNotFoundException()
        }
        throw IocInstanceNotUniqueException()
    }

    override fun <T> getInstances(beanType: Class<T>): Set<T> {
        return HashSet(applicationContext.getBeansOfType(beanType).values)
    }

    fun <T> getByBeanName(beanName: String): T {
        return beanName.let { applicationContext.getBean(it) } as T
    }
}
