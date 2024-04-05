package yang.yu.tmall.domain

import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.domain.sales.OrderLine
import java.math.BigDecimal
import java.math.BigDecimal.valueOf
import java.math.RoundingMode

internal class OrderLineTest : BaseUnitTest() {
    @Test
    fun calculateSubTotal() {
        val category = ProductCategory("a")
        val product = Product("pencil", category)

        val orderLine = OrderLine(product, 3.0, valueOf(150), BigDecimal(30))
        assertThat(orderLine.subTotal.setScale(2, RoundingMode.HALF_UP))
          .isEqualTo(valueOf(150 * 3 * (100 - 30) / 100).setScale(2, RoundingMode.HALF_UP))
    }
}
