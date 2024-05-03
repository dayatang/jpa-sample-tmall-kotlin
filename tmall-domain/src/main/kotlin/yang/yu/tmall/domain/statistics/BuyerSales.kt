package yang.yu.tmall.domain.statistics

import yang.yu.tmall.domain.buyers.Buyer
import java.math.BigDecimal

data class BuyerSales(val buyer: Buyer, val amount: BigDecimal)
