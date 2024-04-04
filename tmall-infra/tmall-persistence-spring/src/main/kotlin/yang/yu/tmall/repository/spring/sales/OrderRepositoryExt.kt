package yang.yu.tmall.repository.spring.sales

import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderQuery
import java.util.stream.Stream

interface OrderRepositoryExt {
  fun find(query: OrderQuery): Stream<Order>
}
