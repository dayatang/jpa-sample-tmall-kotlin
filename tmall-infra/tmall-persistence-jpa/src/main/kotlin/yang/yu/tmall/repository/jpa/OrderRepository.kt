package yang.yu.tmall.repository.jpa

import jakarta.persistence.EntityManager
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderQuery
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.domain.sales.ProductSalesSummary
import java.time.*
import java.util.stream.Stream

class OrderRepository(private val entityManager: EntityManager) :
  AbstractRepository<Order>(entityManager, Order::class.java), Orders {

  override fun find(query: OrderQuery): Stream<Order> {
    val criteria = ArrayList<String>()
    val params = HashMap<String, Any>()
    if (query.id != null) {
      criteria.add("o.id = :id")
      params["id"] = query.id!!
    }
    if (query.buyer != null) {
      criteria.add("o.buyer = :buyer")
      params["buyer"] = query.buyer!!
    }
    if (query.buyerName != null) {
      criteria.add("b.name = :buyerName")
      params["buyerName"] = query.buyerName!!
    }
    if (query.isOrgBuyer != null && query.isOrgBuyer!!) {
      criteria.add("TYPE(b) = OrgBuyer")
    }
    if (query.isPersonalBuyer != null && query.isPersonalBuyer!!) {
      criteria.add("TYPE(b) = PersonalBuyer")
    }
    if (query.orderNo != null) {
      criteria.add("o.orderNo = :orderNo")
      params["orderNo"] = query.orderNo!!
    }
    if (query.product != null) {
      criteria.add("li.product = :product")
      params["product"] = query.product!!
    }
    if (query.createdFrom != null) {
      criteria.add("o.created >= :createdFrom")
      params["createdFrom"] = query.createdFrom!!
    }
    if (query.createdUntil != null) {
      criteria.add("o.created < :createdUntil")
      params["createdUntil"] = query.createdUntil!!
    }
    if (query.shipToProvince != null) {
      criteria.add("sa.province = :shipToProvince")
      params["shipToProvince"] = query.shipToProvince!!
    }
    if (query.shipToCity != null) {
      criteria.add("sa.city = :shipToCity")
      params["shipToCity"] = query.shipToCity!!
    }
    if (query.receiverName != null) {
      criteria.add("sa.receiver = :receiverName")
      params["receiverName"] = query.receiverName!!
    }
    if (query.receiverPhone != null) {
      criteria.add("sa.receiverPhone = :receiverPhone")
      params["receiverPhone"] = query.receiverPhone!!
    }
    if (query.totalPriceLessThan != null) {
      criteria.add("o.totalPrice < :totalPriceLessThan")
      params["totalPriceLessThan"] = query.totalPriceLessThan!!
    }
    if (query.totalPriceNotLessThan != null) {
      criteria.add("o.totalPrice >= :totalPriceNotLessThan")
      params["totalPriceNotLessThan"] = query.totalPriceNotLessThan!!
    }

    var jpql = "select distinct o from Order o"
    if (query.hasBuyerQuery()) jpql += " left join o.buyer b"
    if (query.hasLineItemQuery()) jpql += " left join o.lineItems li"
    if (query.hasAddressQuery()) jpql += " left join o.shippingAddress sa"
    if (criteria.isNotEmpty()) {
      jpql = jpql + " where " + criteria.joinToString(" and ")
    }
    jpql += " order by o.created desc"

    var typedQuery = entityManager.createQuery(jpql, Order::class.java)
    for (param in params) typedQuery = typedQuery.setParameter(param.key, param.value)
    return typedQuery.resultStream
  }

  override fun sumOfSalesAmount(from: LocalDate, until: LocalDate): Money {
    val jpql = "select new yang.yu.tmall.domain.commons.Money(sum(o.totalPrice.value)) from Order o" +
      " where o.created >= :from and o.created < :until"
    return entityManager.createQuery(jpql, Money::class.java)
      .setParameter("from", from.atStartOfDay(ZoneId.systemDefault()).toInstant())
      .setParameter("until", until.atStartOfDay(ZoneId.systemDefault()).toInstant())
      .singleResult?: Money.ZERO
  }

  override fun sumOfSalesByProduct(from: LocalDate, until: LocalDate): Stream<ProductSalesSummary> {
    val jpql = "select ol.product as product, sum(ol.quantity) as quantity, " +
      " new yang.yu.tmall.domain.commons.Money(sum(ol.subTotal.value)) as amount" +
      " from OrderLine ol join ol.order o" +
      " where o.createdDate >= :from and o.createdDate < :until group by ol.product"
    return entityManager.createQuery(jpql, ProductSalesSummary::class.java)
      .setParameter("from", from)
      .setParameter("until", until)
      .resultStream
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
