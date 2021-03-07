package yang.yu.tmall.domain.sales

import java.util.stream.Stream

interface OrderStatusTransitions {
    fun findByOrder(order: Order): Stream<OrderStatusTransition>
}