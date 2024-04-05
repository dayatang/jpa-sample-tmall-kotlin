package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.buyers.Buyer
import java.math.BigDecimal

data class BuyerSales(val buyer: Buyer, val amount: BigDecimal)
