package yang.yu.tmall.domain

import org.assertj.core.data.Percentage
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.products.ProductCategory
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.OrderLine
import java.math.BigDecimal

internal class OrderTest : BaseUnitTest() {
    @Test
    fun calculateTotalPrice() {
        val category = ProductCategory("foods")
        val orderLine1 = OrderLine()
        orderLine1.product = Product("a", category)
        orderLine1.unitPrice = valueOf(150)
        orderLine1.quantity = BigDecimal(3)
        orderLine1.discountRate = BigDecimal(30)
        val orderLine2 = OrderLine()
        orderLine2.product = Product("b", category)
        orderLine2.unitPrice = valueOf(25.4)
        orderLine2.quantity = BigDecimal(3.2)
        orderLine2.discountRate = BigDecimal(0)
        val order = Order()
        order.addLineItem(orderLine1)
        order.addLineItem(orderLine2)
        val subtotal1 = (150 * 3 * (100 - 30) / 100).toDouble()
        //System.out.println(subtotal1);
        val subtotal2 = 25.4 * 3.2
        //System.out.println(subtotal2);
        assertThat(order.totalPrice.value.toDouble())
            .isCloseTo(subtotal1 + subtotal2, Percentage.withPercentage(0.00001))
    }
}
