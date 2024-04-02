package yang.yu.lang

/**
 * IoC容器异常。当访问IoC容器（Spring，Guice等）发生异常时抛出本异常或其子类实例。
 */
open class IocException : RuntimeException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean)
            : super(message, cause, enableSuppression, writableStackTrace)
}