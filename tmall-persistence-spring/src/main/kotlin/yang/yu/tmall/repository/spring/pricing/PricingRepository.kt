package yang.yu.tmall.repository.spring.pricing

import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.Pricings
import yang.yu.tmall.repository.spring.AbstractRepository
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream

/**
 * 定价仓储的实现
 */
@Repository
interface PricingRepository : Pricings, AbstractRepository<Pricing> {

  fun findFirstByProductAndEffectiveInstantIsLessThanEqualOrderByEffectiveInstantDesc(
    product: Product, time: Instant): Optional<Pricing>

  fun findByProductOrderByEffectiveInstant(product: Product): Stream<Pricing>

    override fun getPricingAt(product: Product, time: Instant): Optional<Pricing> =
        findFirstByProductAndEffectiveInstantIsLessThanEqualOrderByEffectiveInstantDesc(product, time)

    override fun findPricingHistoryOfProduct(product: Product): Stream<Pricing> =
        findByProductOrderByEffectiveInstant(product)

}
