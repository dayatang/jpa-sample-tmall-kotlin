package yang.yu.lang

/**
 * 依赖注入容器门面类。用于获取容器管理的各种Bean
 */
object IoC {

  private lateinit var provider: InstanceProvider

  /**
   * 根据类型获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。如果找不到该类型的实例则抛出异常。
   *
   * @param <T>      类型参数
   * @param beanType 实例的类型
   * @return 指定类型的实例。
  </T> */
  fun <T> getInstance(beanType: Class<T>): T {
    return provider.getInstance(beanType)
  }

  /**
   * 根据类型和名称获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。不同的IoC容器用不同的方式解释beanName。
   * 具体的解释方式请参见各种InstanceProvider实现类的Javadoc。
   * 如果找不到该类型的实例则抛出异常。
   *
   * @param <T>      类型参数
   * @param beanName 实现类在容器中配置的名字
   * @param beanType 实例的类型
   * @return 指定类型的实例。
  </T> */
  fun <T> getInstance(beanType: Class<T>, beanName: String): T {
    return provider.getInstance(beanType, beanName)
  }

  /**
   * 根据类型和Annotation获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。不同的IoC容器用不同的方式解释annotation。
   * 具体的解释方式请参见各种InstanceProvider实现类的Javadoc。
   * 如果找不到该类型的实例则抛出异常。
   *
   * @param <T>            类型参数
   * @param beanType       实例的类型
   * @param annotationType 实现类的annotation类型
   * @return 指定类型的实例。
  </T> */
  fun <T> getInstance(beanType: Class<T>, annotationType: Class<out Annotation>): T {
    return provider.getInstance(beanType, annotationType)
  }

  /**
   * 获取指定类型的实例的集合
   *
   * @param beanType 实例的类型
   * @param <T>      类型参数
   * @return 指定类型的实例的集合
  </T> */
  fun <T> getInstances(beanType: Class<T>): Set<T> {
    return provider.getInstances(beanType)
  }
}
