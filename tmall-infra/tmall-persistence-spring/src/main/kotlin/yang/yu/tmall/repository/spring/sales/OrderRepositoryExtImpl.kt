package yang.yu.tmall.repository.spring.sales

import jakarta.inject.Named
import jakarta.persistence.EntityManager
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderQuery
import java.util.stream.Stream

@Named
class OrderRepositoryExtImpl(private val entityManager: EntityManager) : OrderRepositoryExt {
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
      criteria.add("sa.receiverName = :receiverName")
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
}
