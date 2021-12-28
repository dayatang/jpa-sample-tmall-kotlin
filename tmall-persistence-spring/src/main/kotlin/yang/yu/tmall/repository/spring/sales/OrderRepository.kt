package yang.yu.tmall.repository.spring.sales

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 订单仓储的实现
 */
@Named
interface OrderRepository : Orders, JpaRepository<Order, Int> {
  /**
   * 根据ID获取订单
   * @param id 订单ID
   * @return 订单
   */
  override fun findById(id: Int): Optional<Order>

    /**
     * 根据订单编号获取订单
     * @param orderNo 订单编号
     * @return 订单
     */
    override fun getByOrderNo(orderNo: String): Optional<Order>

    override fun findByBuyer(buyer: Buyer): Stream<Order> {
        return findByBuyerOrderByCreatedDesc(buyer)
    }

    fun findByBuyerOrderByCreatedDesc(buyer: Buyer): Stream<Order>

    @Query("select o.order from OrderLine o where o.product = :product order by o.order.created desc")
    override fun findByProduct(@Param("product") product: Product): Stream<Order>

    @Query("select o.order from OrderLine o where o.product = :product and o.created >= :fromTime" +
            " and o.created < :untilTime order by o.order.created desc")
    override fun findByProduct(@Param("product") product: Product,
                               @Param("fromTime") from: LocalDateTime,
                               @Param("untilTime") until: LocalDateTime): Stream<Order>

    @Query("select o from Order o join o.buyer b where TYPE(b) = OrgBuyer order by o.created desc")
    override fun findByOrgBuyers(): Stream<Order>
}
