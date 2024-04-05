package yang.yu.tmall.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.OrgBuyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.repository.jpa.OrderRepository
import java.math.BigDecimal
import jakarta.transaction.Transactional
import yang.yu.tmall.domain.sales.OrderQuery
import java.time.LocalDate
import java.math.BigDecimal.valueOf

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
        val category = entityManager.merge(ProductCategory("a"))
        product1 = entityManager.merge(Product("电冰箱", category))
        product2 = entityManager.merge(Product("电视机", category))
        buyer1 = entityManager.merge(PersonalBuyer("张三"))
        buyer2 = entityManager.merge(OrgBuyer("华为公司"))
        lineItem1 = OrderLine(product1, BigDecimal(3), valueOf(3500))
        lineItem2 = OrderLine(product1, BigDecimal(5), valueOf(3500))
        lineItem3 = OrderLine(product2, BigDecimal(3), valueOf(8500))
        lineItem4 = OrderLine(product2, BigDecimal(2), valueOf(8500))
        order1 = createOrder("order1", buyer1, lineItem1, lineItem3)
        order2 = createOrder("order2", buyer1, lineItem2)
        order3 = createOrder("order3", buyer2, lineItem2, lineItem3)
    }

    private fun createOrder(orderNo: String, buyer: Buyer, vararg orderLines: OrderLine): Order {
        val order = Order(orderNo, buyer)
        orderLines.forEach { order.addLineItem(it) }
        return entityManager.merge(order)
    }

    @AfterEach
    fun afterEach() {
        listOf(order1, order2, order3)
            .forEach(orders::delete)
        listOf(product1, product2, buyer1, buyer2)
            .forEach(entityManager::remove)
    }

    @Test
    fun findById() {
        listOf(order1, order2).forEach {
                assertThat(
                    orders.findById(it.id)
                ).containsSame(it)
            }
        }

    @Test
    fun getByOrderNo() {
            listOf(order1, order2).forEach {
                assertThat(orders.getByOrderNo(it.orderNo))
                  .containsSame(it)
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
  fun findByProductBetween() {
    assertThat(orders.findByProduct(product2,
      LocalDate.now().atStartOfDay(),
      LocalDate.now().plusDays(1).atStartOfDay()))
      .contains(order1)
      .doesNotContain(order2)
  }

    @Test
    fun findByOrgBuyers() {
        assertThat(orders.findByOrgBuyers())
            .contains(order3)
            .doesNotContain(order1, order2)
    }

  @Test
  fun findByQuery() {
    assertThat(orders.find(OrderQuery().isOrgBuyer()))
      .contains(order3)
      .doesNotContain(order1, order2)

    val query = OrderQuery().isPersonalBuyer()
      .buyerName("张三")
      .totalPriceNotLessThan(BigDecimal.valueOf(5000))

    assertThat(orders.find(query))
      .contains(order1, order2)
      .doesNotContain(order3)

  }

  @Test
  fun sumOfSalesAmount() {
    val amount = orders.sumOfSalesAmount(LocalDate.now(), LocalDate.now().plusDays(1))
    println(amount)
  }


  @Test
  fun sumOfSalesByProduct() {
    val results = orders.sumOfSalesByProduct(LocalDate.now(), LocalDate.now().plusDays(1))
    results.forEach { println( "${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun sumOfSalesByYear() {
    val amount = orders.sumOfSalesByYear()
    amount.forEach{ println("${it.yearOrMonth} = ${it.amount}") }
  }

  @Test
  fun sumOfSalesByMonth() {
    val amount = orders.sumOfSalesByMonth(LocalDate.now().year)
    amount.forEach{ println("${it.yearOrMonth} = ${it.amount}") }
  }

  @Test
  fun bestSellNByQuantity() {
    val results = orders.bestSellProductByQuantity(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============bestSellNByQuantity:")
    results.forEach { println( "${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun worstSellNByQuantity() {
    val results = orders.worstSellProductByQuantity(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============worstSellNByQuantity:")
    results.forEach { println( "${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun bestSellNByAmount() {
    val results = orders.bestSellProductByAmount(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============bestSellNByAmount:")
    results.forEach { println( "${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun worstSellNBAmount() {
    val results = orders.worstSellProductBAmount(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============worstSellNBAmount:")
    results.forEach { println( "${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }


  @Test
  fun topNBuyer() {
    val results = orders.topNBuyer(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============topNBuyer:")
    results.forEach { println( "${it.buyer} amount = ${it.amount}") }
  }

  @Test
  fun bottomNBuyer() {
    val results = orders.bottomNBuyer(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============bottomNBuyer:")
    results.forEach { println( "${it.buyer} amount = ${it.amount}") }
  }

}
