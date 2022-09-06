package yang.yu.tmall.repository.spring.sales

import org.springframework.data.repository.query.Param
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.repository.spring.AbstractRepository
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 订单仓储的实现
 */
@Named
class OrderRepository(private val jpa: OrderJpa) : Orders, AbstractRepository<Order>(jpa) {

    /**
     * 根据订单编号获取订单
     * @param orderNo 订单编号
     * @return 订单
     */
    override fun getByOrderNo(orderNo: String): Optional<Order> = jpa.getByOrderNo(orderNo)

    override fun findByBuyer(buyer: Buyer): Stream<Order> = jpa.findByBuyerOrderByCreatedDesc(buyer)

    override fun findByProduct(@Param("product") product: Product): Stream<Order> = jpa.findByProduct(product)

    override fun findByProduct(product: Product, from: LocalDateTime, until: LocalDateTime): Stream<Order> =
      jpa.findByProduct(product, from, until)

    override fun findByOrgBuyers(): Stream<Order> = jpa.findByOrgBuyers()
}
