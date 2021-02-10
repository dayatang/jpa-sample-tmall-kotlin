package yang.yu.tmall.domain

import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import yang.yu.tmall.domain.commons.Money.add
import yang.yu.tmall.domain.commons.Money.subtract
import yang.yu.tmall.domain.commons.Money.multiply
import yang.yu.tmall.domain.commons.Money.divide
import yang.yu.tmall.domain.sales.OrderLine.product
import yang.yu.tmall.domain.sales.OrderLine.setUnitPrice
import yang.yu.tmall.domain.sales.OrderLine.setQuantity
import yang.yu.tmall.domain.sales.OrderLine.setDiscountRate
import yang.yu.tmall.domain.sales.Order.addLineItem
import yang.yu.tmall.domain.sales.Order.totalPrice
import yang.yu.tmall.domain.commons.Money.value
import yang.yu.tmall.domain.sales.OrderLine.subTotal
import yang.yu.tmall.domain.BaseUnitTest
import yang.yu.tmall.domain.commons.Money
import java.math.BigDecimal
import yang.yu.tmall.domain.products.ProductCategory
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.products.Product
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

internal class MoneyTest : BaseUnitTest() {
    @Test
    fun valueOfBigDecimal() {
        assertThat<Any>(valueOf(BigDecimal("15.554"))).isEqualTo(valueOf("15.55"))
        assertThat<Any>(valueOf(BigDecimal("15.555"))).isEqualTo(valueOf("15.56"))
    }

    @Test
    fun valueOfInt() {
        val value = 10
        assertThat<Any>(valueOf(value)).isEqualTo(valueOf("10.00"))
    }

    @Test
    fun valueOfLong() {
        val value = 10000000L
        assertThat<Any>(valueOf(value)).isEqualTo(valueOf("10000000.00"))
    }

    @Test
    fun valueOfDouble() {
        val value = 10.556
        assertThat<Any>(valueOf(value)).isEqualTo(valueOf("10.56"))
    }

    @Test
    fun add() {
        val money1 = valueOf(67)
        val money2 = valueOf(46)
        assertThat(money1.add(money2)).isEqualTo(valueOf("113.00"))
    }

    @Test
    fun subtract() {
        val money1 = valueOf(67)
        val money2 = valueOf(46)
        assertThat(money1.subtract(money2)).isEqualTo(valueOf("21.00"))
    }

    @Test
    fun multiplyByInt() {
        assertThat(valueOf(67).multiply(2)).isEqualTo(valueOf("134.00"))
    }

    @Test
    fun multiplyByLong() {
        assertThat(valueOf(67).multiply(2L)).isEqualTo(valueOf("134.00"))
    }

    @Test
    fun multiplyByDouble() {
        val money = valueOf(6.79)
        assertThat(money.multiply(2.5)).isEqualTo(valueOf("16.98"))
    }

    @Test
    fun multiplyByBigDecimal() {
        val money = valueOf(67)
        val m = BigDecimal(3)
        assertThat(money.multiply(m)).isEqualTo(valueOf("201.00"))
    }

    @Test
    fun dividedByInt() {
        val money = valueOf(68)
        assertThat(money.divide(2)).isEqualTo(valueOf("34.00"))
    }

    @Test
    fun dividedByLong() {
        val money = valueOf(68)
        assertThat(money.divide(2L)).isEqualTo(valueOf("34.00"))
    }

    @Test
    fun testToString() {
        assertThat(valueOf(15.554).toString()).isEqualTo("15.55")
        assertThat(valueOf(15.555).toString()).isEqualTo("15.56")
    }
}