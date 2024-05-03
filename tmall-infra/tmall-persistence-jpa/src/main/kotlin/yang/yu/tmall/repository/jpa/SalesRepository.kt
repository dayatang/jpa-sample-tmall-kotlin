package yang.yu.tmall.repository.jpa

import jakarta.persistence.EntityManager
import yang.yu.tmall.domain.sales.BuyerSales
import yang.yu.tmall.domain.sales.ProductSalesSummary
import yang.yu.tmall.domain.sales.Sales
import yang.yu.tmall.domain.sales.YearMonthSales
import yang.yu.tmall.domain.orders.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId
import java.util.stream.Stream

class SalesRepository(private val entityManager: EntityManager) :
  AbstractRepository<Order>(entityManager, Order::class.java), Sales {

  override fun sumOfSalesAmount(from: LocalDate, until: LocalDate): BigDecimal {
    val jpql = "select sum(o.totalPrice) from Order o" +
      " where o.created >= :from and o.created < :until"
    return entityManager.createQuery(jpql, BigDecimal::class.java)
      .setParameter("from", from.atStartOfDay(ZoneId.systemDefault()).toInstant())
      .setParameter("until", until.atStartOfDay(ZoneId.systemDefault()).toInstant())
      .singleResult ?: BigDecimal.ZERO
  }

  override fun sumOfSalesByProduct(from: LocalDate, until: LocalDate): Stream<ProductSalesSummary> {
    val jpql = "select ol.product as product, sum(ol.quantity) as quantity, " +
      " sum(ol.subTotal) as amount" +
      " from Order o join o.lineItems ol " +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product"
    return entityManager.createQuery(jpql, ProductSalesSummary::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

  override fun sumOfSalesByYear(): Stream<YearMonthSales> {
    val jpql = "select new yang.yu.tmall.domain.sales.YearMonthSales(o.year, sum(o.totalPrice))" +
      " from Order o group by o.year"
    return entityManager.createQuery(jpql, YearMonthSales::class.java).resultStream
  }

  override fun sumOfSalesByMonth(year: Int): Stream<YearMonthSales> {
    val jpql = "select new yang.yu.tmall.domain.sales.YearMonthSales(o.month, sum(o.totalPrice))" +
      " from Order o where o.year = :year group by o.month"
    return entityManager.createQuery(jpql, YearMonthSales::class.java)
      .setParameter("year", year)
      .resultStream
  }

  override fun bestSellProductByQuantity(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    val jpql = "select ol.product as product, sum(ol.quantity) as quantity, sum(ol.subTotal) as amount" +
      " from Order o join o.lineItems ol " +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product" +
      " order by quantity desc limit " + limit
    return entityManager.createQuery(jpql, ProductSalesSummary::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

  override fun worstSellProductByQuantity(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    val jpql = "select ol.product as product, sum(ol.quantity) as quantity, sum(ol.subTotal) as amount" +
      " from Order o join o.lineItems ol " +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product" +
      " order by quantity limit " + limit
    return entityManager.createQuery(jpql, ProductSalesSummary::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

  override fun bestSellProductByAmount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    val jpql = "select ol.product as product, sum(ol.quantity) as quantity, sum(ol.subTotal) as amount" +
      " from Order o join o.lineItems ol " +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product" +
      " order by amount desc limit " + limit
    return entityManager.createQuery(jpql, ProductSalesSummary::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

  override fun worstSellProductByAmount(from: LocalDate, until: LocalDate, limit: Int): Stream<ProductSalesSummary> {
    val jpql = "select ol.product as product, sum(ol.quantity) as quantity, sum(ol.subTotal) as amount" +
      " from Order o join o.lineItems ol " +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product" +
      " order by amount limit " + limit
    return entityManager.createQuery(jpql, ProductSalesSummary::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

  override fun topNBuyer(from: LocalDate, until: LocalDate, limit: Int): Stream<BuyerSales> {
    val jpql = "select o.buyer as buyer, sum(o.totalPrice) as amount from Order o" +
      " where o.createdDate >= :from and o.createdDate < :until group by o.buyer" +
      " order by amount desc limit " + limit
    return entityManager.createQuery(jpql, BuyerSales::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

  override fun bottomNBuyer(from: LocalDate, until: LocalDate, limit: Int): Stream<BuyerSales> {
    val jpql = "select o.buyer as buyer, sum(o.totalPrice) as amount from Order o" +
      " where o.createdDate >= :from and o.createdDate < :until group by o.buyer" +
      " order by amount limit " + limit
    return entityManager.createQuery(jpql, BuyerSales::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
  }

}
