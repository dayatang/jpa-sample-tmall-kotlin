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
import org.assertj.core.data.Percentage
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.sales.Order

internal class OrderTest : BaseUnitTest() {
    @Test
    fun calculateTotalPrice() {
        val category = ProductCategory("foods")
        val orderLine1 = OrderLine()
        orderLine1.product = Product("a", category)
        orderLine1.setUnitPrice(valueOf(150))
        orderLine1.setQuantity(BigDecimal(3))
        orderLine1.setDiscountRate(BigDecimal(30))
        val orderLine2 = OrderLine()
        orderLine2.product = Product("b", category)
        orderLine2.setUnitPrice(valueOf(25.4))
        orderLine2.setQuantity(BigDecimal(3.2))
        orderLine2.setDiscountRate(BigDecimal(0))
        val order = Order()
        order.addLineItem(orderLine1)
        order.addLineItem(orderLine2)
        val subtotal1 = (150 * 3 * (100 - 30) / 100).toDouble()
        //System.out.println(subtotal1);
        val subtotal2 = 25.4 * 3.2
        //System.out.println(subtotal2);
        assertThat(order.totalPrice!!.value.toDouble())
            .isCloseTo(subtotal1 + subtotal2, Percentage.withPercentage(0.00001))
    }
}