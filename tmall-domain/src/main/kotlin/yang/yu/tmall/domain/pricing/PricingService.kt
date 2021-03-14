package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.products.Product
import java.time.LocalDateTime
import java.util.function.Consumer
import java.util.stream.Stream
import javax.inject.Named

@Named
class PricingService(private val pricings: Pricings) {
    fun setPriceOfProduct(product: Product, unitPrice: Money, effectiveTime: LocalDateTime): Pricing {
        return pricings.save(Pricing(product, unitPrice, effectiveTime))
    }

    fun adjustPriceByPercentage(product: Product, percentage: Int, effectiveTime: LocalDateTime): Pricing {
        val newPrice = currentPriceOfProduct(product).multiply(100 + percentage).divide(100)
        return setPriceOfProduct(product, newPrice, effectiveTime)
    }

    fun adjustPriceByPercentage(products: Set<Product>, percentage: Int, effectiveTime: LocalDateTime) {
        products.forEach(Consumer { product: Product -> adjustPriceByPercentage(product, percentage, effectiveTime) })
    }

    fun priceOfProductAt(product: Product, time: LocalDateTime): Money {
        return pricings.getPricingAt(product, time)
                .map { it.unitPrice }
                .orElseThrow { PricingException(product.name + "'s price has not been set yet.") }!!
    }

    fun currentPriceOfProduct(product: Product): Money {
        return pricings.getPricingAt(product, LocalDateTime.now())
                .map { it.unitPrice }
                .orElseThrow { PricingException(product.name + "'s price has not been set yet.") }!!
    }

    fun pricingHistoryOfProduct(product: Product): Stream<Pricing> {
        return pricings.findPricingHistoryOfProduct(product)
    }
}
