package yang.yu.tmall.repository.jpa

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import javax.persistence.EntityManager

class OrderRepository(private val entityManager: EntityManager) : Orders {
    override fun getById(id: Int): Optional<Order> {
        return Optional.ofNullable(entityManager.find(Order::class.java, id))
    }

    override fun getByOrderNo(orderNo: String): Optional<Order> {
        return entityManager.createQuery("select o from Order o where o.orderNo = :orderNo", Order::class.java)
            .setParameter("orderNo", orderNo)
            .resultStream
            .findAny()
    }

    override fun findByBuyer(buyer: Buyer): Stream<Order> {
        return entityManager.createQuery("select o from Order o where o.buyer = :buyer", Order::class.java)
            .setParameter("buyer", buyer)
            .resultStream
    }

    override fun findByProduct(product: Product): Stream<Order> {
        val jpql = "select o.order from OrderLine o where o.product = :product order by o.order.created desc "
        return entityManager.createQuery(jpql, Order::class.java)
            .setParameter("product", product)
            .resultStream
    }

    override fun findByProduct(product: Product, from: LocalDateTime, until: LocalDateTime): Stream<Order> {
        val jpql = "select o.order from OrderLine o where o.product = :product " +
                "and o.created >= :fromTime and o.created < :untilTime order by o.order.created desc "
        return entityManager.createQuery(jpql, Order::class.java)
            .setParameter("product", product)
            .setParameter("fromTime", from)
            .setParameter("untilTime", until)
            .resultStream
    }

    override fun findByOrgBuyers(): Stream<Order> {
        val jpql = "select o from Order o join o.buyer b where TYPE(b) = OrgBuyer"
        return entityManager.createQuery(jpql, Order::class.java)
            .resultStream
    }

    override fun <S: Order> save(entity: S): S {
        return entityManager.merge(entity)
    }

    override fun delete(entity: Order) {
        entityManager.remove(entity)
    }

    override fun findAll(): List<Order> {
        return entityManager.createQuery("select t from Order t", Order::class.java).resultList
    }
}
