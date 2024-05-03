package yang.yu.tmall.domain.statistics

import yang.yu.tmall.domain.commons.BaseRepository
import yang.yu.tmall.domain.sales.Order
import java.math.BigDecimal
import java.time.LocalDate
import java.util.stream.Stream

/**
 * 订单仓储接口
 */
interface Sales : BaseRepository<Order> {

  fun sumOfSalesAmount(from: LocalDate, until: LocalDate): BigDecimal

  fun sumOfSalesByProduct(from: LocalDate, until: LocalDate): Stream<ProductSalesSummary>

  fun sumOfSalesByYear(): Stream<YearMonthSales>

  fun sumOfSalesByMonth(year: Int): Stream<YearMonthSales>

  fun bestSellProductByQuantity(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary>

  fun worstSellProductByQuantity(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary>

  fun bestSellProductByAmount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary>

  fun worstSellProductByAmount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary>

  fun topNBuyer(from: LocalDate, until: LocalDate, limit: Int): Stream<BuyerSales>

  fun bottomNBuyer(from: LocalDate, until: LocalDate, limit: Int): Stream<BuyerSales>

}
