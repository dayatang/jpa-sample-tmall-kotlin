package yang.yu.tmall.domain.pricing

import java.math.BigDecimal

operator fun BigDecimal.plus(value: Number): BigDecimal = this.add(value.toBigDecimal())

operator fun BigDecimal.minus(value: Number): BigDecimal = this.subtract(value.toBigDecimal())

operator fun BigDecimal.times(value: Number): BigDecimal = this.multiply(value.toBigDecimal())

operator fun BigDecimal.div(value: Number): BigDecimal = this.divide(value.toBigDecimal())

operator fun BigDecimal.rem(value: Number): BigDecimal = this.rem(value.toBigDecimal())

fun Number.toBigDecimal(): BigDecimal = when (this) {
  is BigDecimal -> this
  is Float -> BigDecimal(this.toDouble())
  is Double -> BigDecimal(this)
  else -> BigDecimal(this.toLong())
}
