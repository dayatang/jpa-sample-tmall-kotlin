package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.commons.Address
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.products.Product
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
open class Order : BaseEntity() {

    @Basic(optional = false)
    @Column(name = "order_no", nullable = false, unique = true)
    open var orderNo: String = ""

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderColumn(name = "seq_no")
    open var lineItems: MutableList<OrderLine> = ArrayList()
        //get() = ArrayList(field)
        set(value) {
            field = value
            this.totalPrice = calculateTotalPrice()
        }

    @ManyToOne
    open var buyer: Buyer? = null

    @Embedded
    open var shippingAddress: Address? = null

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "total_price"))
    open var totalPrice: Money = Money.ZERO

    fun addLineItem(lineItem: OrderLine) {
        if (containsProduct(lineItem.product!!)) {
            throw DuplicateOrderLineException()
        }
        lineItem.order = this
        lineItems.add(lineItem)
        this.totalPrice = calculateTotalPrice()
    }

    private fun containsProduct(product: Product): Boolean {
        return lineItems.stream()
            .map { it.product }
            .anyMatch {product == it }
    }

    private fun calculateTotalPrice(): Money {
        return lineItems.stream()
            .map { it.subTotal }
            .reduce(Money.ZERO) { subTotal: Money, each: Money -> subTotal.add(each) }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Order) {
            return false
        }
        return orderNo == o.orderNo
    }

    override fun hashCode(): Int {
        return Objects.hash(orderNo)
    }

    override fun toString(): String {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                '}'
    }
}
