package yang.yu.tmall.repository.spring.statistics

import org.springframework.data.domain.Limit
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.statistics.BuyerSales
import yang.yu.tmall.domain.statistics.ProductSalesSummary
import yang.yu.tmall.domain.statistics.Sales
import yang.yu.tmall.domain.statistics.YearMonthSales
import yang.yu.tmall.domain.sales.*
import yang.yu.tmall.repository.spring.AbstractRepository
import java.math.BigDecimal
import java.time.LocalDate
import java.util.stream.Stream

/**
 * 订单仓储的实现
 */
@Repository
interface SalesRepository : Sales, AbstractRepository<Order> {

  @Query(
    "select sum(o.totalPrice) from Order o" +
      " where o.createdDate >= :from and o.createdDate < :until"
  )
  override fun sumOfSalesAmount(@Param("from") from: LocalDate, @Param("until") until: LocalDate): BigDecimal

  @Query(
    "select new yang.yu.tmall.domain.statistics.ProductSalesSummary(ol.product, " +
      " sum(ol.quantity), sum(ol.subTotal))" +
      " from Order o join o.lineItems ol where o.createdDate >= :from and o.createdDate < :until group by ol.product"
  )
  override fun sumOfSalesByProduct(
    @Param("from") from: LocalDate,
    @Param("until") until: LocalDate
  ): Stream<ProductSalesSummary>

  @Query(
    "select new yang.yu.tmall.domain.statistics.YearMonthSales(o.year, sum(o.totalPrice))" +
      " from Order o group by o.year"
  )
  override fun sumOfSalesByYear(): Stream<YearMonthSales>

  @Query(
    "select new yang.yu.tmall.domain.statistics.YearMonthSales(o.month, sum(o.totalPrice))" +
      " from Order o where o.year = :year group by o.month"
  )
  override fun sumOfSalesByMonth(@Param("year") year: Int): Stream<YearMonthSales>

  @Query(
    "select new yang.yu.tmall.domain.statistics.ProductSalesSummary(ol.product as product, sum(ol.quantity) as quantity, " +
      "sum(ol.subTotal) as amount) from Order o join o.lineItems ol" +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product order by quantity desc"
  )
  fun bestSellNByQuantity(
    @Param("from") from: LocalDate,
    @Param("until") until: LocalDate,
    limit: Limit
  ): Stream<ProductSalesSummary>

  override fun bestSellProductByQuantity(
    @Param("from") from: LocalDate,
    @Param("until") until: LocalDate,
    limit: Int,
  ): Stream<ProductSalesSummary> = bestSellNByQuantity(from, until, Limit.of(limit))

  @Query(
    "select new yang.yu.tmall.domain.statistics.ProductSalesSummary(ol.product as product, " +
      "sum(ol.quantity) as quantity, sum(ol.subTotal) as amount)" +
      " from Order o join o.lineItems ol" +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product order by quantity"
  )
  fun worstSellNByQuantity(
    @Param("from") from: LocalDate,
    @Param("until") until: LocalDate,
    limit: Limit
  ): Stream<ProductSalesSummary>

  override fun worstSellProductByQuantity(
    @Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int
  ): Stream<ProductSalesSummary> = worstSellNByQuantity(from, until, Limit.of(limit))

  @Query(
    "select new yang.yu.tmall.domain.statistics.ProductSalesSummary(ol.product as product, sum(ol.quantity) as quantity, " +
      "sum(ol.subTotal) as amount) from Order o join o.lineItems ol" +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product order by amount desc"
  )
  fun bestSellNByAmount(
    @Param("from") from: LocalDate,
    @Param("until") until: LocalDate,
    limit: Limit
  ): Stream<ProductSalesSummary>

  override fun bestSellProductByAmount(
    @Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int
  ): Stream<ProductSalesSummary> = bestSellNByAmount(from, until, Limit.of(limit))

  @Query(
    "select new yang.yu.tmall.domain.statistics.ProductSalesSummary(ol.product as product, sum(ol.quantity) as quantity, " +
      "sum(ol.subTotal) as amount) from Order o join o.lineItems ol" +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product order by amount"
  )
  fun worstSellNByAmount(
    @Param("from") from: LocalDate,
    @Param("until") until: LocalDate,
    limit: Limit
  ): Stream<ProductSalesSummary>

  override fun worstSellProductByAmount(
    @Param("from") from: LocalDate, @Param("until") until: LocalDate, limit: Int
  ): Stream<ProductSalesSummary> = worstSellNByAmount(from, until, Limit.of(limit))

  @Query(
    "select new yang.yu.tmall.domain.statistics.BuyerSales(o.buyer as buyer, sum(o.totalPrice) as amount)" +
      " from Order o where o.createdDate >= :from and o.createdDate < :until group by o.buyer order by amount desc "
  )
  fun topNBuyer(from: LocalDate, until: LocalDate, limit: Limit): Stream<BuyerSales>

  override fun topNBuyer(from: LocalDate, until: LocalDate, limit: Int): Stream<BuyerSales> =
    topNBuyer(from, until, Limit.of(limit))

  @Query(
    "select new yang.yu.tmall.domain.statistics.BuyerSales(o.buyer as buyer, sum(o.totalPrice) as amount)" +
      " from Order o where o.createdDate >= :from and o.createdDate < :until group by o.buyer order by amount "
  )
  fun bottomNBuyer(from: LocalDate, until: LocalDate, limit: Limit): Stream<BuyerSales>

  override fun bottomNBuyer(from: LocalDate, until: LocalDate, limit: Int): Stream<BuyerSales> =
    bottomNBuyer(from, until, Limit.of(limit))
}
