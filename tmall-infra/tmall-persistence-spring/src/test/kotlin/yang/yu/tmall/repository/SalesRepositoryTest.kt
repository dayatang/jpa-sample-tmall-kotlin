package yang.yu.tmall.repository

import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.OrgBuyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.domain.statistics.Sales
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.spring.JpaSpringConfig
import java.math.BigDecimal
import java.time.LocalDate

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
open class SalesRepositoryTest : WithAssertions {

  @Inject
  private lateinit var orders: Orders

  @Inject
  private lateinit var sales: Sales

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
    lineItem1 = OrderLine(product1, 3.0, BigDecimal.valueOf(3500))
    lineItem2 = OrderLine(product1, 5.0, BigDecimal.valueOf(3500))
    lineItem3 = OrderLine(product2, 3.0, BigDecimal.valueOf(8500))
    lineItem4 = OrderLine(product2, 2.0, BigDecimal.valueOf(8500))
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
      .forEach(entityManager::remove)
  }

  @Test
  fun sumOfSalesAmount() {
    val amount = sales.sumOfSalesAmount(LocalDate.now(), LocalDate.now().plusDays(1))
    println(amount)
  }

  @Test
  fun sumOfSalesByProduct() {
    val results = sales.sumOfSalesByProduct(LocalDate.now(), LocalDate.now().plusDays(1))
    results.forEach { println("${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun sumOfSalesByYear() {
    val amount = sales.sumOfSalesByYear()
    amount.forEach { println("${it.yearOrMonth} = ${it.amount}") }
  }

  @Test
  fun sumOfSalesByMonth() {
    val amount = sales.sumOfSalesByMonth(LocalDate.now().year)
    amount.forEach { println("${it.yearOrMonth} = ${it.amount}") }
  }

  @Test
  fun bestSellNByQuantity() {
    val results = sales.bestSellProductByQuantity(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============bestSellNByQuantity:")
    results.forEach { println("${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun worstSellNByQuantity() {
    val results = sales.worstSellProductByQuantity(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============worstSellNByQuantity:")
    results.forEach { println("${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun bestSellNByAmount() {
    val results = sales.bestSellProductByAmount(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============bestSellNByAmount:")
    results.forEach { println("${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun worstSellNByAmount() {
    val results = sales.worstSellProductByAmount(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============worstSellNBAmount:")
    results.forEach { println("${it.product} amount = ${it.amount}, quantity = ${it.quantity}") }
  }

  @Test
  fun topNBuyer() {
    val results = sales.topNBuyer(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============topNBuyer:")
    results.forEach { println("${it.buyer} amount = ${it.amount}") }
  }

  @Test
  fun bottomNBuyer() {
    val results = sales.bottomNBuyer(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 10)
    println("============bottomNBuyer:")
    results.forEach { println("${it.buyer} amount = ${it.amount}") }
  }
}
