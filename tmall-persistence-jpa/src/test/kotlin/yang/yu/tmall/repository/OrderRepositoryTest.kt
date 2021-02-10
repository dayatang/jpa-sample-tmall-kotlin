package yang.yu.tmall.repository

import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import yang.yu.tmall.domain.sales.Order.orderNo
import yang.yu.tmall.domain.sales.Order.buyer
import yang.yu.tmall.domain.sales.Orders.getById
import yang.yu.tmall.domain.commons.BaseEntity.id
import yang.yu.tmall.domain.sales.Orders.getByOrderNo
import yang.yu.tmall.domain.sales.Orders.findByBuyer
import yang.yu.tmall.domain.buyers.Buyer.equals
import yang.yu.tmall.domain.sales.Orders.findByProduct
import yang.yu.tmall.domain.sales.Orders.findByOrgBuyers
import yang.yu.tmall.domain.buyers.Buyers.save
import yang.yu.tmall.domain.buyers.Buyers.findAll
import yang.yu.tmall.domain.buyers.Buyers.getById
import yang.yu.tmall.domain.buyers.Buyers.getByName
import yang.yu.tmall.domain.buyers.Buyers.findByNameStartsWith
import yang.yu.tmall.domain.buyers.Buyers.findByNameContains
import yang.yu.tmall.domain.buyers.Buyers.delete
import yang.yu.tmall.domain.buyers.Buyer.name
import yang.yu.tmall.domain.buyers.PersonalBuyer.setImInfo
import yang.yu.tmall.domain.buyers.Buyers.findPersonalBuyerByQQ
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.*
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import yang.yu.tmall.repository.BaseIntegrationTest
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.buyers.OrgBuyer
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.repository.jpa.BuyerRepositoryJpql
import yang.yu.tmall.repository.BuyerRepositoryJpqlTest
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.repository.jpa.BuyerRepositoryCriteria
import yang.yu.tmall.repository.BuyerRepositoryCriteriaTest
import yang.yu.tmall.repository.jpa.OrderRepository
import java.util.*
import java.util.function.Consumer
import javax.transaction.Transactional

@Transactional
class OrderRepositoryTest : BaseIntegrationTest() {
    private var orders: Orders? = null
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
        orders = OrderRepository(entityManager!!)
        product1 = entityManager!!.merge(Product("电冰箱", null))
        product2 = entityManager!!.merge(Product("电视机", null))
        buyer1 = entityManager!!.merge(PersonalBuyer("张三"))
        buyer2 = entityManager!!.merge(OrgBuyer("华为公司"))
        lineItem1 = OrderLine(product1, 3, valueOf(3500))
        lineItem2 = OrderLine(product1, 5, valueOf(3500))
        lineItem3 = OrderLine(product2, 3, valueOf(8500))
        lineItem4 = OrderLine(product2, 2, valueOf(8500))
        order1 = createOrder("order1", buyer1, lineItem1!!, lineItem3!!)
        order2 = createOrder("order2", buyer1, lineItem2!!)
        order3 = createOrder("order3", buyer2, lineItem2!!, lineItem3!!)
    }

    private fun createOrder(orderNo: String, buyer: Buyer?, vararg orderLines: OrderLine): Order {
        val order = Order()
        order.orderNo = orderNo
        order.buyer = buyer
        Arrays.stream(orderLines).forEach { lineItem: OrderLine? -> order.addLineItem(lineItem) }
        return entityManager!!.merge(order)
    }

    @AfterEach
    fun afterEach() {
        Arrays.asList(order1, order2, order3)
            .forEach(Consumer { order: Order? -> orders!!.delete(order) })
        Arrays.asList(product1, product2, buyer1, buyer2)
            .forEach(Consumer { o: BaseEntity? -> entityManager!!.remove(o) })
    }

    @get:Test
    val byId: Unit
        get() {
            Arrays.asList(order1, order2).forEach(Consumer { order: Order ->
                assertThat(
                    orders!!.getById(order.id)
                ).containsSame(order)
            })
        }

    @get:Test
    val byOrderNo: Unit
        get() {
            Arrays.asList(order1, order2).forEach(Consumer { order: Order ->
                assertThat(
                    orders!!.getByOrderNo(order.orderNo)
                ).containsSame(order)
            })
        }

    @Test
    fun findByBuyer() {
        assertThat(orders!!.findByBuyer(buyer1)).hasSize(2).allMatch { order: Order -> order.buyer!!.equals(buyer1) }
    }

    @Test
    fun findByProduct() {
        assertThat(orders!!.findByProduct(product2))
            .contains(order1)
            .doesNotContain(order2)
    }

    @Test
    fun findByOrgBuyers() {
        assertThat(orders!!.findByOrgBuyers())
            .contains(order3)
            .doesNotContain(order1, order2)
    }
}