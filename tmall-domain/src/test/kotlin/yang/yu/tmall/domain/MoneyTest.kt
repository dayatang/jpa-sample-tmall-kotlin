package yang.yu.tmall.domain

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import java.math.BigDecimal

internal class MoneyTest : BaseUnitTest() {
    @Test
    fun valueOfBigDecimal() {
        assertThat<Any>(valueOf(BigDecimal("15.554"))).isEqualTo(valueOf("15.554"))
        assertThat<Any>(valueOf(BigDecimal("15.555"))).isEqualTo(valueOf("15.555"))
    }

    @Test
    fun valueOfInt() {
        val value = 10
        assertThat<Any>(valueOf(value)).isEqualTo(valueOf("10"))
    }

    @Test
    fun valueOfLong() {
        val value = 10000000L
        assertThat<Any>(valueOf(value)).isEqualTo(valueOf("10000000"))
    }

    @Test
    fun valueOfDouble() {
        val value = 10.556
        assertThat<Any>(valueOf(value)).isEqualTo(valueOf("10.556"))
    }

    @Test
    fun add() {
        val money1 = valueOf(67)
        val money2 = valueOf(46)
        assertThat(money1.add(money2)).isEqualTo(valueOf("113"))
    }

    @Test
    fun subtract() {
        val money1 = valueOf(67)
        val money2 = valueOf(46)
        assertThat(money1.subtract(money2)).isEqualTo(valueOf("21"))
    }

    @Test
    fun multiplyByInt() {
        assertThat(valueOf(67).multiply(2)).isEqualTo(valueOf("134"))
    }

    @Test
    fun multiplyByLong() {
        assertThat(valueOf(67).multiply(2L)).isEqualTo(valueOf("134"))
    }

    @Test
    fun multiplyByDouble() {
        val money = valueOf(6.79)
        assertThat(money.multiply(2.5)).isEqualTo(valueOf("16.975"))
    }

    @Test
    fun multiplyByBigDecimal() {
        val money = valueOf(67)
        val m = BigDecimal(3)
        assertThat(money.multiply(m)).isEqualTo(valueOf("201"))
    }

    @Test
    fun dividedByInt() {
        val money = valueOf(68)
        assertThat(money.divide(2)).isEqualTo(valueOf("34"))
    }

    @Test
    fun dividedByLong() {
        val money = valueOf(68)
        assertThat(money.divide(2L)).isEqualTo(valueOf("34"))
    }

    @Disabled
    @Test
    fun testToString() {
        assertThat(valueOf(15.554).toString()).isEqualTo("15.554")
        assertThat(valueOf(15.555).toString()).isEqualTo("15.555")
    }
}