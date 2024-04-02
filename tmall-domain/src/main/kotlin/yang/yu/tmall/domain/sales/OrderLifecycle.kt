package yang.yu.tmall.domain.sales

import yang.yu.lang.IoC
import java.util.*
import java.util.stream.Collectors

class OrderLifecycle private constructor(private val order: Order) {
  private var transitions: OrderStatusTransitions? = null
    get() = field ?: IoC.getInstance(OrderStatusTransitions::class.java)

  private val currentTransition: OrderStatusTransition? = transitionList.lastOrNull()

  val currentStatus: OrderStatus = currentTransition?.status?: OrderStatus.PENDING

  private val transitionList: List<OrderStatusTransition>
    get() = transitions!!.findByOrder(order)
      .sorted(Comparator.comparing { it.occurredOn })
      .collect(Collectors.toList())

  companion object {
    fun of(order: Order): OrderLifecycle {
      return OrderLifecycle(order)
    }
  }
}
