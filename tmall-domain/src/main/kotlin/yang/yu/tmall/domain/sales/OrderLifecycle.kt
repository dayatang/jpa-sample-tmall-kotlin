package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.commons.Money.multiply
import yang.yu.tmall.domain.commons.Money.divide
import yang.yu.tmall.domain.commons.Money.subtract
import yang.yu.tmall.domain.commons.IoC.getInstance
import yang.yu.tmall.domain.commons.BaseEntity
import javax.persistence.OrderColumn
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.buyers.Buyer
import javax.persistence.Embedded
import javax.persistence.AttributeOverride
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.sales.DuplicateOrderLineException
import yang.yu.tmall.domain.products.Product
import java.util.function.BinaryOperator
import java.time.LocalDateTime
import javax.persistence.JoinColumn
import java.math.BigDecimal
import kotlin.jvm.JvmOverloads
import yang.yu.tmall.domain.sales.OrderStatusTransitions
import yang.yu.tmall.domain.commons.IoC
import yang.yu.tmall.domain.sales.OrderStatusTransition
import yang.yu.tmall.domain.sales.OrderStatus
import java.util.stream.Collectors
import yang.yu.tmall.domain.sales.OrderLifecycle
import javax.persistence.Enumerated
import java.lang.RuntimeException
import yang.yu.tmall.domain.products.ProductCategory
import java.util.*

class OrderLifecycle private constructor(private val order: Order) {
    private var transitions: OrderStatusTransitions? = null
        private get() = Optional.ofNullable(field)
            .orElse(getInstance(OrderStatusTransitions::class.java))
        set
    val currentTransition: OrderStatusTransition
        get() {
            val transitionList = transitionList
            return transitionList[transitionList.size - 1]
        }
    val currentStatus: OrderStatus?
        get() = currentTransition.status
    val transitionList: List<OrderStatusTransition>
        get() = transitions!!.findByOrder(order)
            .sorted(Comparator.comparing { obj: OrderStatusTransition -> obj.seqNo })
            .collect(Collectors.toList())

    companion object {
        fun of(order: Order): OrderLifecycle {
            return OrderLifecycle(order)
        }
    }
}