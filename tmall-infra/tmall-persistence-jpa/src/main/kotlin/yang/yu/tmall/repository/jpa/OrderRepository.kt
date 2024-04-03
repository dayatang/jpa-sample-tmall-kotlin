package yang.yu.tmall.repository.jpa

import jakarta.persistence.EntityManager
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream

class OrderRepository(private val entityManager: EntityManager) :
  AbstractRepository<Order>(entityManager, Order::class.java), Orders {

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
}
