package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.Pricings
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.spring.JpaSpringConfig
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
open class PricingRepositoryTest : WithAssertions {

    @Inject
    private lateinit var pricings: Pricings

    @Inject
    private lateinit var entityManager: EntityManager

    private lateinit var product1: Product
    private lateinit var product2: Product
    private lateinit var pricing1: Pricing
    private lateinit var pricing2: Pricing
    private lateinit var pricing3: Pricing
    private lateinit var pricing4: Pricing

    @BeforeEach
    fun beforeEach() {
        val category = entityManager.merge(ProductCategory("a"))
        product1 = entityManager.merge(Product("电冰箱", category))
        product2 = entityManager.merge(Product("电视机", category))
        pricing1 = entityManager.merge(Pricing(product1, Money.valueOf(500), LocalDate.of(2020, 10, 1).atStartOfDay()))
        pricing2 = entityManager.merge(Pricing(product1, Money.valueOf(600), LocalDate.of(2020, 2, 15).atStartOfDay()))
        pricing3 = entityManager.merge(Pricing(product2, Money.valueOf(7000), LocalDate.of(2020, 7, 14).atStartOfDay()))
        pricing4 = entityManager.merge(Pricing(product2, Money.valueOf(7100), LocalDate.of(2020, 2, 15).atStartOfDay()))
    }

    @AfterEach
    fun afterEach() {
        listOf(product1, product2, pricing1, pricing2, pricing3, pricing4)
                .forEach(entityManager::remove)
    }

    @Test
    fun getPriceAt() {
            val time2002_02_15: LocalDateTime = LocalDate.of(2020, 2, 15).atStartOfDay()
            val time2002_02_16: LocalDateTime = LocalDate.of(2020, 2, 16).atStartOfDay()
            val time2002_10_01: LocalDateTime = LocalDate.of(2020, 10, 1).atStartOfDay()
            assertThat(pricings.getPricingAt(product1, time2002_02_15))
                    .map(Pricing::unitPrice)
                    .contains(Money.valueOf(600))
            assertThat(pricings.getPricingAt(product1, time2002_02_16))
                    .map(Pricing::unitPrice)
                    .contains(Money.valueOf(600))
            assertThat(pricings.getPricingAt(product1, time2002_10_01))
                    .map(Pricing::unitPrice)
                    .contains(Money.valueOf(500))
        }
}
