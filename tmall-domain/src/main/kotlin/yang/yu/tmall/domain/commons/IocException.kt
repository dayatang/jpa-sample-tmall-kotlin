package yang.yu.tmall.domain.commons

import yang.yu.tmall.domain.commons.InstanceProvider
import yang.yu.tmall.domain.commons.IoC
import javax.persistence.Embeddable
import java.math.BigDecimal
import yang.yu.tmall.domain.commons.Money
import javax.persistence.GeneratedValue
import java.time.LocalDateTime
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.PostLoad
import java.lang.RuntimeException
import yang.yu.tmall.domain.commons.IocException

/**
 * IoC容器异常。当访问IoC容器（Spring，Guice等）发生异常时抛出本异常或其子类实例。
 */
open class IocException : RuntimeException {
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