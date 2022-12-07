package yang.yu.tmall.repository.spring.pricing

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.Pricings
import yang.yu.tmall.repository.spring.AbstractRepository
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import jakarta.inject.Named

/**
 * 定价仓储的实现
 */
@Named
class PricingRepository(private val jpa: PricingJpa) : Pricings, AbstractRepository<Pricing>(jpa) {

    override fun getPricingAt(product: Product, time: LocalDateTime): Optional<Pricing> =
        jpa.findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(product, time)

    override fun findPricingHistoryOfProduct(product: Product): Stream<Pricing> =
        jpa.findByProductOrderByEffectiveTime(product)

}
