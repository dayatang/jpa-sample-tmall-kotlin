package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import java.math.BigDecimal
import jakarta.persistence.*

@Entity
@Table(name = "order_lines")
data class OrderLine(
    @ManyToOne(optional = false)
    @JoinColumn(name = "prod_id")
    val product: Product,

    val quantity: BigDecimal = BigDecimal.ZERO,

    @AttributeOverride(name = "value", column = Column(name = "unit_price"))
    val unitPrice: Money = Money.ZERO,

    @Column(name = "discount_rate")
    val discountRate: BigDecimal = BigDecimal.ZERO
) : BaseEntity() {

    @ManyToOne(optional = false)
    lateinit var order: Order

    @AttributeOverride(name = "value", column = Column(name = "sub_total"))
    var subTotal: Money = Money.ZERO
        get() {
            if (isNew) {
                calculateSubTotal()
            }
            return field
        }

    constructor(product: Product, quantity: Double = 0.0, unitPrice: Money = Money.ZERO, discountRate: BigDecimal = BigDecimal.ZERO) : this(
        product,
        BigDecimal.valueOf(quantity),
        unitPrice,
        discountRate
    )

    @PreUpdate
    fun calculateSubTotal() {
        val base = unitPrice.times(quantity)
        val discountMoney = base.times(discountRate).div(100)
        subTotal = base.minus(discountMoney)
    }

}
