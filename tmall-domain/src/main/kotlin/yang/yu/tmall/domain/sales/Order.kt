package yang.yu.tmall.domain.sales

import jakarta.persistence.*
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.Address
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
@Table(name = "orders")
data class Order(
  @Column(name = "order_no", unique = true)
  val orderNo: String,

  @ManyToOne
  val buyer: Buyer,

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
  @AttributeOverride(name = "value", column = Column(name = "total_price", precision = 15, scale = 4))
  var totalPrice: Money = Money.ZERO

  @Column(name = "created_date")
  val createdDateTime = ZonedDateTime.ofInstant(created, ZoneId.systemDefault())

  @Column(name = "created_year")
  val year: Int = createdDateTime.year

  @Column(name = "created_month")
  val month: Int = createdDateTime.monthValue

  @Column(name = "created_day")
  val day: Int = createdDateTime.dayOfMonth

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
      .reduceOrNull(Money::plus)?: Money.ZERO
  }
}
