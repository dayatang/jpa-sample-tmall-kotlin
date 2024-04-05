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

    @Column(name = "unit_price", precision = 15, scale = 4)
    val quantity: BigDecimal = BigDecimal.ZERO,

    @Column(name = "unit_price", precision = 15, scale = 4)
    val unitPrice: Money = Money.ZERO,

    @Column(name = "discount_rate", precision = 15, scale = 4)
    val discountRate: BigDecimal = BigDecimal.ZERO
) : BaseEntity() {

    @ManyToOne(optional = false)
    lateinit var order: Order

    @AttributeOverride(name = "value", column = Column(name = "sub_total", precision = 15, scale = 4))
    var subTotal: Money = Money.ZERO
        get() {
            if (isNew) {
              executeBeforeSave()
            }
            return field
        }

    constructor(product: Product, quantity: Double = 0.0, unitPrice: Money = Money.ZERO, discountRate: BigDecimal = BigDecimal.ZERO) : this(
        product,
        BigDecimal.valueOf(quantity),
        unitPrice,
        discountRate
    )

    override fun executeBeforeSave() {
        val base = unitPrice.times(quantity)
        val discountMoney = base.times(discountRate).div(100)
        subTotal = base.minus(discountMoney)
    }

}
