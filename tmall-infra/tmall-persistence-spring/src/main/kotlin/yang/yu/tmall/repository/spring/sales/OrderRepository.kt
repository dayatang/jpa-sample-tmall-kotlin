package yang.yu.tmall.repository.spring.sales

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.domain.sales.ProductSalesSummary
import yang.yu.tmall.repository.spring.AbstractRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.stream.Stream

/**
 * 订单仓储的实现
 */
@Repository
interface OrderRepository : Orders, AbstractRepository<Order>, OrderRepositoryExt {

  //@Query("select Money(sum(o.totalPrice)) from Order o where o.created >= :from and o.created < :until")
  @Query("select new yang.yu.tmall.domain.commons.Money(sum(o.totalPrice.value)) from Order o")
  override fun sumOfSalesAmount(from: LocalDate, until: LocalDate): Money

  override fun sumOfSalesByProduct(from: LocalDate, until: LocalDate): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun sumOfSalesByYear(from: LocalDate, until: LocalDate): Stream<Pair<Int, Money>> {
    TODO("Not yet implemented")
  }

  override fun sumOfSalesByMonth(from: LocalDate, until: LocalDate, year: Int): Stream<Pair<Int, Money>> {
    TODO("Not yet implemented")
  }

  override fun bestSellNByCount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun worstSellNByCount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun bestSellNByAmount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun worstSellNBAmount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  fun toInstant(value: LocalDate) = value.atStartOfDay().toInstant(ZoneOffset.UTC)

  fun toInstant(value: LocalDateTime) = value.toInstant(ZoneOffset.UTC)

  fun toInstant(value: ZonedDateTime) = value.toInstant()
}
