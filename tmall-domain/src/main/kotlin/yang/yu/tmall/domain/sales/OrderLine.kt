package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.products.Product
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "order_lines")
open class OrderLine : BaseEntity {

    @ManyToOne(optional = false)
    open var order: Order? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "prod_id")
    open var product: Product? = null

    open var quantity: BigDecimal = BigDecimal.ZERO
        set(value) {
            field = value
            this.subTotal = calculateSubTotal()
        }

    @AttributeOverride(name = "value", column = Column(name = "unit_price"))
    open var unitPrice: Money = Money.ZERO
        set(value) {
            field = value
            this.subTotal = calculateSubTotal()
        }

    @Column(name = "discount_rate")
    open var discountRate: BigDecimal = BigDecimal.ZERO
        set(value) {
            field = value
            this.subTotal = calculateSubTotal()
        }

    @AttributeOverride(name = "value", column = Column(name = "sub_total"))
    open var subTotal: Money = Money.ZERO

    constructor() {}

    constructor(product: Product, quantity: BigDecimal = BigDecimal.ZERO, unitPrice: Money = Money.ZERO) {
        this.product = product
        this.quantity = quantity
        this.unitPrice = unitPrice
        calculateSubTotal()
    }

    constructor(product: Product, quantity: Double = 0.0, unitPrice: Money = Money.ZERO) : this(
        product,
        BigDecimal.valueOf(quantity),
        unitPrice
    ) {
    }

    private fun calculateSubTotal(): Money {
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
