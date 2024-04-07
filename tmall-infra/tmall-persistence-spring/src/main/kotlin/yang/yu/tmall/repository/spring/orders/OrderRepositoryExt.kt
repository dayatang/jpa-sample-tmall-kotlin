package yang.yu.tmall.repository.spring.orders

import yang.yu.tmall.domain.orders.Order
import yang.yu.tmall.domain.orders.OrderQuery
import java.util.stream.Stream

interface OrderRepositoryExt {
  fun find(query: OrderQuery): Stream<Order>
}
