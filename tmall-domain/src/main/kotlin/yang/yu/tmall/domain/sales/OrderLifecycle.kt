package yang.yu.tmall.domain.sales

import yang.yu.lang.IoC.getInstance
import java.util.*
import java.util.stream.Collectors

class OrderLifecycle private constructor(private val order: Order) {
  private var transitions: OrderStatusTransitions? = null
    private get() = Optional.ofNullable(field)
      .orElse(getInstance(OrderStatusTransitions::class.java))

  private val currentTransition: OrderStatusTransition
    get() {
      val transitionList = transitionList
      return transitionList[transitionList.size - 1]
    }

  val currentStatus: OrderStatus
    get() = currentTransition.status

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
