package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.assertj.core.util.Sets
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.PricingException
import yang.yu.tmall.domain.pricing.PricingService
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.products.ProductCategory
import yang.yu.tmall.spring.JpaSpringConfig
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])

@Transactional
open class PricingServiceTest : WithAssertions {

    @Inject
    private lateinit var service: PricingService

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
        pricing1 = service.setPrice(product1, Money.valueOf(500), LocalDate.of(2020, 10, 1).atStartOfDay())
        pricing2 = service.setPrice(product1, Money.valueOf(600), LocalDate.of(2020, 2, 15).atStartOfDay())
        pricing3 = service.setPrice(product2, Money.valueOf(7000), LocalDate.of(2020, 7, 14).atStartOfDay())
        pricing4 = service.setPrice(product2, Money.valueOf(7100), LocalDate.of(2020, 2, 15).atStartOfDay())
    }

    @AfterEach
    fun afterEach() {
        listOf(product1, product2, pricing1, pricing2, pricing3, pricing4)
                .forEach(entityManager::remove)
    }

    @Test
    fun currentPrice() {
            assertThat(service.currentPrice(product1)).isEqualTo(Money.valueOf(500))
    }

    @Test
    fun priceAt() {
            val time2002_02_15: LocalDateTime = LocalDate.of(2020, 2, 15).atStartOfDay()
            val time2002_02_16: LocalDateTime = LocalDate.of(2020, 2, 16).atStartOfDay()
            val time2002_10_01: LocalDateTime = LocalDate.of(2020, 10, 1).atStartOfDay()
            assertThat(service.priceAt(product1, time2002_02_15)).isEqualTo(Money.valueOf(600))
            assertThat(service.priceAt(product1, time2002_02_16)).isEqualTo(Money.valueOf(600))
            assertThat(service.priceAt(product1, time2002_10_01)).isEqualTo(Money.valueOf(500))
    }

    @Test
    fun adjustPriceByPercentage() {
        val time2002_11_01: LocalDateTime = LocalDate.of(2020, 11, 1).atStartOfDay()
        val time2002_10_31: LocalDateTime = time2002_11_01.minusSeconds(10)
        val productSet: Set<Product> = Sets.newLinkedHashSet(product1, product2)
        service.adjustPriceByPercentage(productSet, 10.0, time2002_11_01)
        println("=======================")
        service.pricingHistory(product1).forEach(System.out::println)
        service.pricingHistory(product2).forEach(System.out::println)
        println("=======================")
        assertThat(service.priceAt(product1, time2002_11_01)).isEqualTo(Money.valueOf(550))
        assertThat(service.priceAt(product2, time2002_11_01)).isEqualTo(Money.valueOf(7700))
        assertThat(service.priceAt(product1, time2002_10_31)).isEqualTo(Money.valueOf(500))
        assertThat(service.priceAt(product2, time2002_10_31)).isEqualTo(Money.valueOf(7000))
    }

    @Test
    fun priceNotSetYet() {
        assertThatThrownBy {
            val time2002_02_14: LocalDateTime = LocalDate.of(2020, 2, 14).atStartOfDay()
            service.priceAt(product1, time2002_02_14)
        }.isInstanceOf(PricingException::class.java)
                .hasMessage("电冰箱's price has not been set yet.")
    }
}
