package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.commons.BaseRepository
import java.util.stream.Stream

interface OrderStatusTransitions : BaseRepository<OrderStatusTransition> {
  fun findByOrder(order: Order): Stream<OrderStatusTransition>
}
