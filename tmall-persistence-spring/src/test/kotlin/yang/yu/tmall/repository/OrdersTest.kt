package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.OrgBuyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.catalogue.Product
import yang.yu.tmall.domain.catalogue.ProductCategory
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.spring.JpaSpringConfig
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
open class OrdersTest : WithAssertions {

    @Inject
    private lateinit var orders: Orders

    @Inject
    private lateinit var entityManager: EntityManager

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
        val category = entityManager.merge(ProductCategory("a"))
        product1 = entityManager.merge(Product("电冰箱", category))
        product2 = entityManager.merge(Product("电视机", category))
        buyer1 = entityManager.merge(PersonalBuyer("张三"))
        buyer2 = entityManager.merge(OrgBuyer("华为公司"))
        lineItem1 = OrderLine(product1, 3.0, Money.valueOf(3500))
        lineItem2 = OrderLine(product1, 5.0, Money.valueOf(3500))
        lineItem3 = OrderLine(product2, 3.0, Money.valueOf(8500))
        lineItem4 = OrderLine(product2, 2.0, Money.valueOf(8500))
        order1 = createOrder("order1", buyer1, lineItem1, lineItem3)
        order2 = createOrder("order2", buyer1, lineItem2)
        order3 = createOrder("order3", buyer2, lineItem2, lineItem3)
    }

    private fun createOrder(orderNo: String, buyer: Buyer, vararg orderLines: OrderLine): Order {
        val order = Order(orderNo, buyer)
        orderLines.forEach(order::addLineItem)
        return entityManager.merge(order)
    }

    @AfterEach
    fun afterEach() {
        listOf(order1, order2, order3)
                .forEach(orders::delete)
        listOf(product1, product2, buyer1, buyer2)
                .forEach (entityManager::remove)
    }

    @Test
    fun getById() {
        listOf(order1, order2).forEach {
            assertThat(orders.getById(it.id)).containsSame(it)
        }
    }

    @Test
    fun getByOrderNo() {
        listOf(order1, order2).forEach {
            assertThat(orders.getByOrderNo(it.orderNo)).containsSame(it)
        }
    }

    @Test
    fun findByBuyer() {
        assertThat(orders.findByBuyer(buyer1))
            .hasSize(2)
            .allMatch { it.buyer == buyer1 }
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
