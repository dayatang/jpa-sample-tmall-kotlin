package yang.yu.tmall.domain.pricing

import yang.yu.lang.IoC
import yang.yu.tmall.domain.catalog.Product
import java.math.BigDecimal
import java.time.Instant

private val pricingService: PricingService
  get() = IoC.getInstance(PricingService::class.java)


private val priceQueryService
  get() = IoC.getInstance(PriceQueryService::class.java)


/**
 * 设定价格，在指定时间生效
 * @param unitPrice 要设置的单价
 * @param effectiveInstant 生效时间
 * @return 一个新的定价对象
 */
fun Product.setPrice(unitPrice: BigDecimal, effectiveInstant: Instant = Instant.now()): Pricing {
  return pricingService.setPrice(this, unitPrice, effectiveInstant)
}

/**
 * 按百分比调整价格，在指定时间生效
 * @param percentage 要调整的单价的百分比。例如10就是价格上调10%，-5就是下调5%
 * @param effectiveInstant 生效时间
 * @return 一个新的定价对象
 */
fun Product.adjustPriceByPercentage(
  percentage: Number,
  effectiveInstant: Instant = Instant.now()
): Pricing {
  return pricingService.adjustPriceByPercentage(this, percentage, effectiveInstant)
}

/**
 * 获取特定时刻的价格
 * @param time 要查询的时刻
 * @return 指定商品在指定时刻的单价
 * @throws PricingException 当商品还没设定单价时抛出此异常
 */
fun Product.priceAt(time: Instant = Instant.now()): BigDecimal {
  return priceQueryService.priceOfProduct(this, time)
}

/**
 * 获取当前价格
 * @return 商品的当前价格
 */
fun Product.currentPrice(): BigDecimal = priceAt()
