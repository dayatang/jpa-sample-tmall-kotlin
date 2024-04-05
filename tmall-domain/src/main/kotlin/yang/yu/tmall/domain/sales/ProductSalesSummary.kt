package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.Money

interface ProductSalesSummary {

  val product: Product

  val count: Long

  val amount: Money

}
