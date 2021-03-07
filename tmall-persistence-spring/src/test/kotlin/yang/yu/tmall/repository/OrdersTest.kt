package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.buyers.Buyer
import java.util.*
import java.util.function.Consumer
import javax.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
class OrdersTest : WithAssertions {
    @Inject
    private val orders: Orders? = null

    @Inject
    private val entityManager: EntityManager? = null
    private var order1: Order? = null
    private var order2: Order? = null
    private var order3: Order? = null
    private var lineItem1: OrderLine? = null
    private var lineItem2: OrderLine? = null
    private var lineItem3: OrderLine? = null
    private var lineItem4: OrderLine? = null
    private var product1: Product? = null
    private var product2: Product? = null
    private var buyer1: PersonalBuyer? = null
    private var buyer2: OrgBuyer? = null
    @BeforeEach
    fun beforeEach() {
        product1 = entityManager.merge(Product("电冰箱", null))
        product2 = entityManager.merge(Product("电视机", null))
        buyer1 = entityManager.merge(PersonalBuyer("张三"))
        buyer2 = entityManager.merge(OrgBuyer("华为公司"))
        lineItem1 = OrderLine(product1, 3, Money.valueOf(3500))
        lineItem2 = OrderLine(product1, 5, Money.valueOf(3500))
        lineItem3 = OrderLine(product2, 3, Money.valueOf(8500))
        lineItem4 = OrderLine(product2, 2, Money.valueOf(8500))
        order1 = createOrder("order1", buyer1, lineItem1, lineItem3)
        order2 = createOrder("order2", buyer1, lineItem2)
        order3 = createOrder("order3", buyer2, lineItem2, lineItem3)
    }

    private fun createOrder(orderNo: String, buyer: Buyer?, vararg orderLines: OrderLine): Order {
        val order = Order()
        order.setOrderNo(orderNo)
        order.setBuyer(buyer)
        Arrays.stream(orderLines).forEach(order::addLineItem)
        return entityManager.merge(order)
    }

    @AfterEach
    fun afterEach() {
        Arrays.asList<Any?>(order1, order2, order3)
                .forEach(orders::delete)
        Arrays.asList<Any?>(product1, product2, buyer1, buyer2)
                .forEach(Consumer { o: Any? -> entityManager.remove(o) })
    }

    @get:Test
    val byId: Unit
        get() {
            Arrays.asList<Any?>(order1, order2).forEach(Consumer { order: Any? -> assertThat(orders.getById(order.getId())).containsSame(order) })
        }

    @get:Test
    val byOrderNo: Unit
        get() {
            Arrays.asList<Any?>(order1, order2).forEach(Consumer { order: Any? -> assertThat(orders.getByOrderNo(order.getOrderNo())).containsSame(order) })
        }

    @Test
    fun findByBuyer() {
        assertThat(orders.findByBuyer(buyer1)).hasSize(2).allMatch { order -> order.getBuyer().equals(buyer1) }
    }

    @Test
    fun findByProduct() {
        assertThat(orders.findByProduct(product2))
                .contains(order1)
                .doesNotContain(order2)
    }

    @Test
    fun findByOrgBuyers() {
        assertThat(orders.findByOrgBuyers())
                .contains(order3)
                .doesNotContain(order1, order2)
    }
}