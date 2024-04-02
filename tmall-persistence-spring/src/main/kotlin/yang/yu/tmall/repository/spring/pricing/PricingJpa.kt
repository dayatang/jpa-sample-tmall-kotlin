package yang.yu.tmall.repository.spring.pricing

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.pricing.Pricing
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import jakarta.inject.Named

/**
 * 定价仓储的实现
 */
@Named
interface PricingJpa : JpaRepository<Pricing, Int> {

    fun findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(
        product: Product, time: LocalDateTime): Optional<Pricing>

    fun findByProductOrderByEffectiveTime(product: Product): Stream<Pricing>
}
