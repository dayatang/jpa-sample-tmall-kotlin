package yang.yu.tmall.domain.sales

import jakarta.persistence.*
import org.slf4j.LoggerFactory
import yang.yu.tmall.domain.catalog.Product
import java.math.BigDecimal
import kotlin.jvm.Transient
import yang.yu.lang.*


@Embeddable
@Table(name = "order_lines")
data class OrderLine(
  @ManyToOne(optional = false)
  @JoinColumn(name = "prod_id")
  val product: Product,

  @Column(name = "quantity", precision = 15, scale = 4)
  val quantity: BigDecimal = BigDecimal.ZERO,

  @Column(name = "unit_price", precision = 15, scale = 4)
  val unitPrice: BigDecimal = BigDecimal.ZERO,

  @Column(name = "discount_rate", precision = 15, scale = 4)
  val discountRate: BigDecimal = BigDecimal.ZERO
) {

  @Column(name = "sub_total", precision = 15, scale = 4)
  var subTotal: BigDecimal = calcSubTotal()

  constructor(
    product: Product,
    quantity: Double = 0.0,
    unitPrice: BigDecimal = BigDecimal.ZERO,
    discountRate: BigDecimal = BigDecimal.ZERO
  ) : this(
    product,
    BigDecimal.valueOf(quantity),
    unitPrice,
    discountRate
  )

  private fun calcSubTotal(): BigDecimal {
    logger.debug("=======calcSubTotal")
    val base = unitPrice * quantity
    val discountBigDecimal = base * discountRate / 100
    return base - discountBigDecimal
  }

  companion object {

    @JvmStatic
    @Transient
    private val logger = LoggerFactory.getLogger(this::class.java)
  }

}
