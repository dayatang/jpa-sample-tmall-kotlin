package yang.yu.tmall.repository.spring.pricing

import yang.yu.tmall.domain.pricing.Pricing
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 定价仓储的实现
 */
@Named
interface PricingRepository : Pricings, JpaRepository<Pricing?, Int?> {
    fun getPricingAt(product: Product?, time: LocalDateTime?): Optional<Pricing?>? {
        return findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(product, time)
    }

    fun findPricingHistoryOfProduct(product: Product?): Stream<Pricing?>? {
        return findByProductOrderByEffectiveTime(product)
    }

    fun findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(product: Product?, time: LocalDateTime?): Optional<Pricing?>?
    fun findByProductOrderByEffectiveTime(product: Product?): Stream<Pricing?>?
}