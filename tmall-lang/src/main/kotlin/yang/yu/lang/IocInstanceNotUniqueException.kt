package yang.yu.lang

/**
 * 当试图获取在IoC容器中不存在的Bean实例时抛出此异常。
 */
class IocInstanceNotUniqueException : IocException {
    constructor()

    constructor(message: String?) : super(message)

    constructor(cause: Throwable?) : super(cause)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

    companion object {
        private const val serialVersionUID = -742775077430352894L
    }
}
