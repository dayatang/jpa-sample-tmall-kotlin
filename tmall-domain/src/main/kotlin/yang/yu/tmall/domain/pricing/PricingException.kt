package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.commons.Money.multiply
import yang.yu.tmall.domain.commons.Money.divide
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.commons.Money
import java.time.LocalDateTime
import kotlin.jvm.JvmOverloads
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.Pricings
import yang.yu.tmall.domain.pricing.PricingException
import java.lang.RuntimeException

class PricingException : RuntimeException {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}
    protected constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace) {
    }
}