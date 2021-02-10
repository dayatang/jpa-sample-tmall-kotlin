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
    var orderNo: String? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderColumn(name = "seq_no")
    private val lineItems: MutableList<OrderLine> = ArrayList()

    @ManyToOne
    var buyer: Buyer? = null

    @Embedded
    var shippingAddress: Address? = null

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "total_price"))
    var totalPrice: Money? = null
    fun getLineItems(): List<OrderLine> {
        return ArrayList(lineItems)
    }

    fun addLineItem(lineItem: OrderLine?) {
        if (lineItem == null) {
            return
        }
        if (containsProduct(lineItem.product)) {
            throw DuplicateOrderLineException()
        }
        lineItem.order = this
        lineItems.add(lineItem)
        calculateTotalPrice()
    }

    private fun containsProduct(product: Product?): Boolean {
        return lineItems.stream()
            .map { obj: OrderLine -> obj.product }
            .anyMatch { o: Product? -> product!!.equals(o) }
    }

    fun removeLineItem(lineItem: OrderLine) {
        lineItem.order = null
        lineItems.remove(lineItem)
        calculateTotalPrice()
    }

    private fun calculateTotalPrice(): Money? {
        totalPrice = lineItems.stream()
            .map { obj: OrderLine -> obj.subTotal }
            .peek { x: Money? -> println(x) }
            .reduce(Money.ZERO) { obj: Money, amount: Money -> obj.add(amount) } as ((Money?, Money?) -> Money?)?
        return totalPrice
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
