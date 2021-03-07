package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.assertj.core.util.Sets
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.commons.Money
import java.util.*
import java.util.function.Consumer
import javax.transaction.Transactional
import kotlin.collections.LinkedHashSet

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
class PricingServiceTest : WithAssertions {
    @Inject
    private val service: PricingService? = null

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
        pricing1 = service.setPriceOfProduct(product1, Money.valueOf(500), LocalDate.of(2020, 10, 1).atStartOfDay())
        pricing2 = service.setPriceOfProduct(product1, Money.valueOf(600), LocalDate.of(2020, 2, 15).atStartOfDay())
        pricing3 = service.setPriceOfProduct(product2, Money.valueOf(7000), LocalDate.of(2020, 7, 14).atStartOfDay())
        pricing4 = service.setPriceOfProduct(product2, Money.valueOf(7100), LocalDate.of(2020, 2, 15).atStartOfDay())
    }

    @AfterEach
    fun afterEach() {
        Arrays.asList<Any?>(product1, product2, pricing1, pricing2, pricing3, pricing4)
                .forEach(Consumer { o: Any? -> entityManager.remove(o) })
    }

    @get:Test
    val currentPrice: Unit
        get() {
            assertThat(service.currentPriceOfProduct(product1)).isEqualTo(Money.valueOf(500))
        }

    @get:Test
    val priceAt: Unit
        get() {
            val time2002_02_15: LocalDateTime = LocalDate.of(2020, 2, 15).atStartOfDay()
            val time2002_02_16: LocalDateTime = LocalDate.of(2020, 2, 16).atStartOfDay()
            val time2002_10_01: LocalDateTime = LocalDate.of(2020, 10, 1).atStartOfDay()
            assertThat(service.priceOfProductAt(product1, time2002_02_15)).isEqualTo(Money.valueOf(600))
            assertThat(service.priceOfProductAt(product1, time2002_02_16)).isEqualTo(Money.valueOf(600))
            assertThat(service.priceOfProductAt(product1, time2002_10_01)).isEqualTo(Money.valueOf(500))
        }

    @Test
    fun adjustPriceByPercentage() {
        val time2002_11_01: LocalDateTime = LocalDate.of(2020, 11, 1).atStartOfDay()
        val time2002_10_31: LocalDateTime = time2002_11_01.minusSeconds(1)
        val productSet: LinkedHashSet<Product?> = Sets.newLinkedHashSet<Product?>(product1, product2)
        service.adjustPriceByPercentage(productSet, 10, time2002_11_01)
        println("=======================")
        service.pricingHistoryOfProduct(product1).forEach(System.out::println)
        service.pricingHistoryOfProduct(product2).forEach(System.out::println)
        println("=======================")
        assertThat(service.priceOfProductAt(product1, time2002_11_01)).isEqualTo(Money.valueOf(550))
        assertThat(service.priceOfProductAt(product2, time2002_11_01)).isEqualTo(Money.valueOf(7700))
        assertThat(service.priceOfProductAt(product1, time2002_10_31)).isEqualTo(Money.valueOf(500))
        assertThat(service.priceOfProductAt(product2, time2002_10_31)).isEqualTo(Money.valueOf(7000))
    }

    @Test
    fun priceNotSetYet() {
        assertThatThrownBy {
            val time2002_02_14: LocalDateTime = LocalDate.of(2020, 2, 14).atStartOfDay()
            service.priceOfProductAt(product1, time2002_02_14)
        }.isInstanceOf(PricingException::class.java)
                .hasMessage("电冰箱's price has not been set yet.")
    }
}