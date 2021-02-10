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
import java.util.function.Consumer
import java.util.stream.Stream
import javax.inject.Named

@Named
class PricingService(private val pricings: Pricings) {
    fun setPriceOfProduct(product: Product?, unitPrice: Money?, effectiveTime: LocalDateTime?): Pricing? {
        return pricings.save(Pricing(product, unitPrice, effectiveTime))
    }

    fun adjustPriceByPercentage(product: Product, percentage: Int, effectiveTime: LocalDateTime?): Pricing {
        val newPrice = currentPriceOfProduct(product).multiply(100 + percentage).divide(100)
        return setPriceOfProduct(product, newPrice, effectiveTime)!!
    }

    fun adjustPriceByPercentage(products: Set<Product>, percentage: Int, effectiveTime: LocalDateTime?) {
        products.forEach(Consumer { product: Product -> adjustPriceByPercentage(product, percentage, effectiveTime) })
    }

    fun priceOfProductAt(product: Product, time: LocalDateTime?): Money {
        return pricings.getPricingAt(product, time)
            .map { obj: Pricing? -> obj.getUnitPrice() }
            .orElseThrow { PricingException(product.name + "'s price has not been set yet.") }!!
    }

    fun currentPriceOfProduct(product: Product): Money {
        return pricings.getPricingAt(product, LocalDateTime.now())
            .map { obj: Pricing? -> obj.getUnitPrice() }
            .orElseThrow { PricingException(product.name + "'s price has not been set yet.") }!!
    }

    fun pricingHistoryOfProduct(product: Product?): Stream<Pricing?>? {
        return pricings.findPricingHistoryOfProduct(product)
    }
}