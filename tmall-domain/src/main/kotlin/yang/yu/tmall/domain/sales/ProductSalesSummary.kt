package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.catalog.Product
import java.math.BigDecimal

data class ProductSalesSummary(val product: Product, val quantity: BigDecimal, val amount: BigDecimal)
