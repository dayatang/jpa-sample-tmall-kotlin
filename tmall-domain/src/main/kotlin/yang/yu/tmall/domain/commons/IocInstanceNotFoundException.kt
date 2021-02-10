package yang.yu.tmall.domain.commons

/**
 * 当试图获取在IoC容器中不存在的Bean实例时抛出此异常。
 */
class IocInstanceNotFoundException : IocException {
    constructor() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace
    ) {
    }
}