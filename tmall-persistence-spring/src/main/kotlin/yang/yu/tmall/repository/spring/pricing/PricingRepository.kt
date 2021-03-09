package yang.yu.tmall.repository.spring.pricing

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.Pricings
import yang.yu.tmall.domain.products.Product
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 定价仓储的实现
 */
@Named
interface PricingRepository : Pricings, JpaRepository<Pricing?, Int?> {

    override fun getPricingAt(product: Product, time: LocalDateTime): Optional<Pricing> {
        return findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(product, time)
    }

    override fun findPricingHistoryOfProduct(product: Product): Stream<Pricing> {
        return findByProductOrderByEffectiveTime(product)
    }

    fun findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(product: Product, time: LocalDateTime): Optional<Pricing>

    fun findByProductOrderByEffectiveTime(product: Product): Stream<Pricing>
}