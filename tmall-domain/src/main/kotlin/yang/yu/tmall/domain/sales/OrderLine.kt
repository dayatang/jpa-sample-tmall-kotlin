package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.commons.Money.multiply
import yang.yu.tmall.domain.commons.Money.divide
import yang.yu.tmall.domain.commons.Money.subtract
import yang.yu.tmall.domain.commons.IoC.getInstance
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.sales.DuplicateOrderLineException
import yang.yu.tmall.domain.products.Product
import java.util.function.BinaryOperator
import java.time.LocalDateTime
import java.math.BigDecimal
import kotlin.jvm.JvmOverloads
import yang.yu.tmall.domain.sales.OrderStatusTransitions
import yang.yu.tmall.domain.commons.IoC
import yang.yu.tmall.domain.sales.OrderStatusTransition
import yang.yu.tmall.domain.sales.OrderStatus
import java.util.stream.Collectors
import yang.yu.tmall.domain.sales.OrderLifecycle
import java.lang.RuntimeException
import yang.yu.tmall.domain.products.ProductCategory
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "order_lines")
class OrderLine : BaseEntity {
    @ManyToOne(optional = false)
    var order: Order? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "prod_id")
    var product: Product? = null
    private var quantity = BigDecimal.ZERO

    @AttributeOverride(name = "value", column = Column(name = "unit_price"))
    private var unitPrice: Money? = null

    @Column(name = "discount_rate")
    private var discountRate = BigDecimal.ZERO

    @AttributeOverride(name = "value", column = Column(name = "sub_total"))
    var subTotal: Money? = null

    constructor() {}
    constructor(product: Product?, quantity: BigDecimal, unitPrice: Money?) {
        this.product = product
        this.quantity = quantity
        this.unitPrice = unitPrice
        calculateSubTotal()
    }

    @JvmOverloads
    constructor(product: Product?, quantity: Double, unitPrice: Money? = Money.ZERO) : this(
        product,
        BigDecimal.valueOf(quantity),
        unitPrice
    ) {
    }

    fun getQuantity(): BigDecimal {
        return quantity
    }

    fun setQuantity(quantity: BigDecimal) {
        this.quantity = quantity
        calculateSubTotal()
    }

    fun getUnitPrice(): Money? {
        return unitPrice
    }

    fun setUnitPrice(unitPrice: Money?) {
        this.unitPrice = unitPrice
        calculateSubTotal()
    }

    fun getDiscountRate(): BigDecimal {
        return discountRate
    }

    fun setDiscountRate(discountRate: BigDecimal) {
        this.discountRate = discountRate
        calculateSubTotal()
    }

    private fun calculateSubTotal(): Money? {
        val base = unitPrice!!.multiply(quantity)
        val discountMoney = base.multiply(discountRate).divide(100)
        subTotal = base.subtract(discountMoney)
        return subTotal
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is OrderLine) {
            return false
        }
        return product == o.product
    }

    override fun hashCode(): Int {
        return Objects.hash(product)
    }

    override fun toString(): String {
        return "OrderLine{" +
                "order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}'
    }
}