package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.assertj.core.util.Sets
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.PricingException
import yang.yu.tmall.domain.pricing.PricingService
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.spring.JpaSpringConfig
import java.time.LocalDate
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import yang.yu.tmall.domain.pricing.PriceQueryService
import java.math.BigDecimal
import java.time.ZoneOffset

@SpringJUnitConfig(classes = [JpaSpringConfig::class])

@Transactional
open class PriceQueryServiceTest : WithAssertions {

  @Inject
  private lateinit var pricingService: PricingService

  @Inject
  private lateinit var priceQueryService: PriceQueryService


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
    pricing1 = pricingService.setPrice(
      product1, BigDecimal.valueOf(500),
      LocalDate.of(2020, 10, 1).atStartOfDay().toInstant(ZoneOffset.UTC)
    )
    pricing2 = pricingService.setPrice(
      product1, BigDecimal.valueOf(600),
      LocalDate.of(2020, 2, 15).atStartOfDay().toInstant(ZoneOffset.UTC)
    )
    pricing3 = pricingService.setPrice(
      product2, BigDecimal.valueOf(7000),
      LocalDate.of(2020, 7, 14).atStartOfDay().toInstant(ZoneOffset.UTC)
    )
    pricing4 = pricingService.setPrice(
      product2, BigDecimal.valueOf(7100),
      LocalDate.of(2020, 2, 15).atStartOfDay().toInstant(ZoneOffset.UTC)
    )
  }

  @AfterEach
  fun afterEach() {
    listOf(product1, product2, pricing1, pricing2, pricing3, pricing4)
      .forEach(entityManager::remove)
  }

  @Test
  fun currentPrice() {
    assertThat(priceQueryService.priceOfProduct(product1)).isEqualTo(BigDecimal.valueOf(500))
  }

  @Test
  fun priceAt() {
    val time2002_02_15 = LocalDate.of(2020, 2, 15).atStartOfDay().toInstant(ZoneOffset.UTC)
    val time2002_02_16 = LocalDate.of(2020, 2, 16).atStartOfDay().toInstant(ZoneOffset.UTC)
    val time2002_10_01 = LocalDate.of(2020, 10, 1).atStartOfDay().toInstant(ZoneOffset.UTC)
    assertThat(priceQueryService.priceOfProduct(product1, time2002_02_15)).isEqualTo(BigDecimal.valueOf(600))
    assertThat(priceQueryService.priceOfProduct(product1, time2002_02_16)).isEqualTo(BigDecimal.valueOf(600))
    assertThat(priceQueryService.priceOfProduct(product1, time2002_10_01)).isEqualTo(BigDecimal.valueOf(500))
  }

  @Test
  fun priceNotSetYet() {
    assertThatThrownBy {
      val time2002_02_14 = LocalDate.of(2020, 2, 14)
        .atStartOfDay().toInstant(ZoneOffset.UTC)
      priceQueryService.priceOfProduct(product1, time2002_02_14)
    }.isInstanceOf(PricingException::class.java)
      .hasMessage("电冰箱's price has not been set yet.")
  }
}
