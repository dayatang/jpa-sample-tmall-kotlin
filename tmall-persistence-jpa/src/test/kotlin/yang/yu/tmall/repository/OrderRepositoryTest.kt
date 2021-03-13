package yang.yu.tmall.repository

import org.junit.jupiter.api.*
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.OrgBuyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.repository.jpa.OrderRepository
import java.util.*
import java.util.function.Consumer
import javax.transaction.Transactional

@Transactional
class OrderRepositoryTest : BaseIntegrationTest() {
    private lateinit var orders: Orders
    private lateinit var order1: Order
    private lateinit var order2: Order
    private lateinit var order3: Order
    private lateinit var lineItem1: OrderLine
    private lateinit var lineItem2: OrderLine
    private lateinit var lineItem3: OrderLine
    private lateinit var lineItem4: OrderLine
    private lateinit var product1: Product
    private lateinit var product2: Product
    private lateinit var buyer1: PersonalBuyer
    private lateinit var buyer2: OrgBuyer

    @BeforeEach
    fun beforeEach() {
        orders = OrderRepository(entityManager)
        product1 = entityManager.merge(Product("电冰箱", null))
        product2 = entityManager.merge(Product("电视机", null))
        buyer1 = entityManager.merge(PersonalBuyer("张三"))
        buyer2 = entityManager.merge(OrgBuyer("华为公司"))
        lineItem1 = OrderLine(product1, 3.0, valueOf(3500))
        lineItem2 = OrderLine(product1, 5.0, valueOf(3500))
        lineItem3 = OrderLine(product2, 3.0, valueOf(8500))
        lineItem4 = OrderLine(product2, 2.0, valueOf(8500))
        order1 = createOrder("order1", buyer1, lineItem1, lineItem3)
        order2 = createOrder("order2", buyer1, lineItem2)
        order3 = createOrder("order3", buyer2, lineItem2, lineItem3)
    }

    private fun createOrder(orderNo: String, buyer: Buyer, vararg orderLines: OrderLine): Order {
        val order = Order()
        order.orderNo = orderNo
        order.buyer = buyer
        Arrays.stream(orderLines).forEach { order.addLineItem(it) }
        return entityManager.merge(order)
    }

    @AfterEach
    fun afterEach() {
        Arrays.asList(order1, order2, order3)
            .forEach(Consumer { orders.delete(it) })
        Arrays.asList(product1, product2, buyer1, buyer2)
            .forEach(Consumer { o: BaseEntity? -> entityManager.remove(o) })
    }

    @get:Test
    val byId: Unit
        get() {
            Arrays.asList(order1, order2).forEach(Consumer { order: Order ->
                assertThat(
                    orders.getById(order.id)
                ).containsSame(order)
            })
        }

    @get:Test
    val byOrderNo: Unit
        get() {
            Arrays.asList(order1, order2).forEach(Consumer { order: Order ->
                assertThat(
                    orders.getByOrderNo(order.orderNo)
                ).containsSame(order)
            })
        }

    @Test
    fun findByBuyer() {
        assertThat(orders.findByBuyer(buyer1)).hasSize(2).allMatch { it.buyer.equals(buyer1) }
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
