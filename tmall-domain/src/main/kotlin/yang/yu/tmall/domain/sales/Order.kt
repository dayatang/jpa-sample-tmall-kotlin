package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.Address
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
  @Column(name = "order_no", unique = true)
  val orderNo: String,

  @ManyToOne
  val buyer: Buyer
) : BaseEntity() {

  @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
  @OrderColumn(name = "seq_no")
  var lineItems: MutableList<OrderLine> = ArrayList()
    //get() = ArrayList(field)
    set(value) {
      field = value
      this.totalPrice = calculateTotalPrice()
    }

  @Embedded
  var shippingAddress: Address? = null

  @Embedded
  @AttributeOverride(name = "value", column = Column(name = "total_price"))
  var totalPrice: Money = Money.ZERO

  fun addLineItem(lineItem: OrderLine) {
    if (containsProduct(lineItem.product)) {
      throw DuplicateOrderLineException()
    }
    lineItem.order = this
    lineItems.add(lineItem)
    this.totalPrice = calculateTotalPrice()
  }

  private fun containsProduct(product: Product): Boolean {
    return lineItems
      .map(OrderLine::product)
      .contains(product)
  }

  private fun calculateTotalPrice(): Money {
    return lineItems
      .map(OrderLine::subTotal)
      .reduceOrNull(Money::add)?: Money.ZERO
  }
}
