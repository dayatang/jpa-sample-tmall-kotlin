/**
 * 定价相关的领域对象
 */
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
