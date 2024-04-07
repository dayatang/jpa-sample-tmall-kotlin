package yang.yu.tmall.domain.orders

import jakarta.persistence.*
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.Address
import yang.yu.tmall.domain.commons.BaseEntity
import java.math.BigDecimal
import java.time.LocalDate
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
  @AttributeOverrides(
    AttributeOverride(name = "province", column = Column(name = "shipping_addr_province")),
    AttributeOverride(name = "city", column = Column(name = "shipping_addr_city")),
    AttributeOverride(name = "detail", column = Column(name = "shipping_addr_detail")),
    AttributeOverride(name = "receiverName", column = Column(name = "shipping_addr_receiver_name")),
    AttributeOverride(name = "receiverPhone", column = Column(name = "shipping_addr_receiver_phone"))
  )
  var shippingAddress: Address? = null

  @Column(name = "total_price", precision = 15, scale = 4)
  var totalPrice: BigDecimal = BigDecimal.ZERO

  @Column(name = "created_time")
  val createdDateTime: ZonedDateTime = ZonedDateTime.ofInstant(created, ZoneId.systemDefault())

  @Column(name = "created_date")
  val createdDate: LocalDate = LocalDate.ofInstant(created, ZoneId.systemDefault())

  @Column(name = "created_year")
  val year: Int = createdDate.year

  @Column(name = "created_month")
  val month: Int = createdDate.monthValue

  @Column(name = "created_day")
  val day: Int = createdDate.dayOfMonth

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

  private fun calculateTotalPrice(): BigDecimal {
    return lineItems
      .map(OrderLine::subTotal)
      .reduceOrNull(BigDecimal::plus) ?: BigDecimal.ZERO
  }

  override fun executeBeforeSave() {
    this.totalPrice = calculateTotalPrice()
  }
}
