package yang.yu.tmall.repository.spring.sales

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.domain.sales.ProductSalesSummary
import yang.yu.tmall.repository.spring.AbstractRepository
import java.time.LocalDate
import java.util.stream.Stream

/**
 * 订单仓储的实现
 */
@Repository
interface OrderRepository : Orders, AbstractRepository<Order>, OrderRepositoryExt {

  @Query("select new yang.yu.tmall.domain.commons.Money(sum(o.totalPrice.value)) from Order o" +
    " where o.createdDate >= :from and o.createdDate < :until")
  override fun sumOfSalesAmount(@Param("from") from: LocalDate, @Param("until") until: LocalDate): Money

  @Query("select new yang.yu.tmall.domain.sales.ProductSalesSummary(ol.product, " +
    "sum(ol.quantity), new yang.yu.tmall.domain.commons.Money(sum(ol.subTotal.value)))" +
    " from OrderLine ol join ol.order o where o.createdDate >= :from and o.createdDate < :until group by ol.product")
  override fun sumOfSalesByProduct(@Param("from") from: LocalDate, @Param("until") until: LocalDate): Stream<ProductSalesSummary>

  override fun sumOfSalesByYear(@Param("from") from: LocalDate, @Param("until") until: LocalDate): Stream<Pair<Int, Money>> {
    TODO("Not yet implemented")
  }

  override fun sumOfSalesByMonth(@Param("from") from: LocalDate, @Param("until") until: LocalDate, year: Int): Stream<Pair<Int, Money>> {
    TODO("Not yet implemented")
  }

  override fun bestSellNByCount(@Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun worstSellNByCount(@Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun bestSellNByAmount(@Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }

  override fun worstSellNBAmount(@Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    TODO("Not yet implemented")
  }
}
