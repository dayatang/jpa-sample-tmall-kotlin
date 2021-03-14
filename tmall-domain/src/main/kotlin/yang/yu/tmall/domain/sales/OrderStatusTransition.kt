package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.commons.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 订单状态迁移实体，记录某个时间点订单进入某个状态。
 */
@Entity
@Table(name = "order_status_transitions")
open class OrderStatusTransition : BaseEntity() {

    @ManyToOne
    open var order: Order? = null

    @Enumerated(EnumType.STRING)
    open var status: OrderStatus = OrderStatus.PENDING

    open var occurredOn = LocalDateTime.now()

    @Column(name = "seq_no")
    open var seqNo = 0
}
