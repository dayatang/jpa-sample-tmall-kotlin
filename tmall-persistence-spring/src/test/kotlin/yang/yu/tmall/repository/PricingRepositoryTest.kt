package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import yang.yu.tmall.domain.commons.Money
import java.util.*
import java.util.function.Consumer
import javax.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
class PricingRepositoryTest : WithAssertions {
    @Inject
    private val pricings: Pricings? = null

    @Inject
    private val entityManager: EntityManager? = null
    private var product1: Product? = null
    private var product2: Product? = null
    private var pricing1: Pricing? = null
    private var pricing2: Pricing? = null
    private var pricing3: Pricing? = null
    private var pricing4: Pricing? = null
    @BeforeEach
    fun beforeEach() {
        product1 = entityManager.merge(Product("电冰箱", null))
        product2 = entityManager.merge(Product("电视机", null))
        pricing1 = entityManager.merge(Pricing(product1, Money.valueOf(500), LocalDate.of(2020, 10, 1).atStartOfDay()))
        pricing2 = entityManager.merge(Pricing(product1, Money.valueOf(600), LocalDate.of(2020, 2, 15).atStartOfDay()))
        pricing3 = entityManager.merge(Pricing(product2, Money.valueOf(7000), LocalDate.of(2020, 7, 14).atStartOfDay()))
        pricing4 = entityManager.merge(Pricing(product2, Money.valueOf(7100), LocalDate.of(2020, 2, 15).atStartOfDay()))
    }

    @AfterEach
    fun afterEach() {
        Arrays.asList<Any?>(product1, product2, pricing1, pricing2, pricing3, pricing4)
                .forEach(Consumer { o: Any? -> entityManager.remove(o) })
    }

    @get:Test
    val priceAt: Unit
        get() {
            val time2002_02_15: LocalDateTime = LocalDate.of(2020, 2, 15).atStartOfDay()
            val time2002_02_16: LocalDateTime = LocalDate.of(2020, 2, 16).atStartOfDay()
            val time2002_10_01: LocalDateTime = LocalDate.of(2020, 10, 1).atStartOfDay()
            assertThat(pricings.getPricingAt(product1, time2002_02_15))
                    .map(Pricing::getUnitPrice)
                    .contains(Money.valueOf(600))
            assertThat(pricings.getPricingAt(product1, time2002_02_16))
                    .map(Pricing::getUnitPrice)
                    .contains(Money.valueOf(600))
            assertThat(pricings.getPricingAt(product1, time2002_10_01))
                    .map(Pricing::getUnitPrice)
                    .contains(Money.valueOf(500))
        }
}