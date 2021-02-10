package yang.yu.tmall.domain

import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import yang.yu.tmall.domain.sales.OrderLine
import java.math.BigDecimal

internal class OrderLineTest : BaseUnitTest() {
    @Test
    fun calculateSubTotal() {
        val orderLine = OrderLine()
        orderLine.setUnitPrice(valueOf(150))
        orderLine.setQuantity(BigDecimal(3))
        orderLine.setDiscountRate(BigDecimal(30))
        assertThat(orderLine.subTotal).isEqualTo(valueOf(150 * 3 * (100 - 30) / 100))
    }
}