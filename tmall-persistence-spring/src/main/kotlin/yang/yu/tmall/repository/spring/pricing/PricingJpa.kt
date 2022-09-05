package yang.yu.tmall.repository.spring.pricing

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.catalogue.Product
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 定价仓储的实现
 */
@Named
interface PricingJpa : JpaRepository<Pricing, Int> {

    fun findFirstByProductAndEffectiveTimeIsLessThanEqualOrderByEffectiveTimeDesc(
        product: Product, time: LocalDateTime): Optional<Pricing>

    fun findByProductOrderByEffectiveTime(product: Product): Stream<Pricing>
}
