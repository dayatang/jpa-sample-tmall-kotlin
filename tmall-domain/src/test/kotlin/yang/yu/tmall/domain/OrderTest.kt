package yang.yu.tmall.domain

import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.buyers.OrgBuyer
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
        val orderLine1 = OrderLine(Product("a", category), BigDecimal(3), valueOf(150), BigDecimal(30))
        val orderLine2 = OrderLine(Product("b", category), BigDecimal(3.2), valueOf(25.4))
        val buyer = OrgBuyer("IBM")
        val order = Order("xxxxxx", buyer)
        order.addLineItem(orderLine1)
        order.addLineItem(orderLine2)
        val subtotal1 = valueOf(150 * 3 * (100 - 30) / 100)
        //System.out.println(subtotal1);
        val subtotal2 = valueOf(25.4 * 3.2)
        //System.out.println(subtotal2);
        assertThat(order.totalPrice.scaledValue).isEqualTo(subtotal1.add(subtotal2).scaledValue)
    }
}
