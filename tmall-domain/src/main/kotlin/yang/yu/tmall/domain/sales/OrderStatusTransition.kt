package yang.yu.tmall.domain.sales

import jakarta.persistence.*
import yang.yu.tmall.domain.commons.BaseEntity
import java.time.Instant

/**
 * 订单状态迁移实体，记录某个时间点订单进入某个状态。
 */
@Entity
@Table(name = "order_status_transitions")
data class OrderStatusTransition(

  @ManyToOne
  val order: Order,

  @Enumerated(EnumType.STRING)
  val status: OrderStatus = OrderStatus.PENDING,

  val occurredOn: Instant = Instant.now(),

  ) : BaseEntity()
