package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.catalog.Product
import jakarta.inject.Named
import java.math.BigDecimal
import java.time.Instant

/**
 * 定价领域服务
 * @param pricings 定价仓储
 */
@Named
class PriceQueryService(private val pricings: Pricings) {

  /**
   * 获取特定时刻的商品价格
   * @param product 要查询价格的商品
   * @param time 要查询的时刻，默认为当前时间
   * @return 指定商品在指定时刻的单价
   * @throws PricingException 当商品还没设定单价时抛出此异常
   */
  fun priceOfProduct(product: Product, time: Instant = Instant.now()): BigDecimal =
    pricings.getPricingAt(product, time)
      .map { it.unitPrice }
      .orElseThrow { PricingException(product.name + "'s price has not been set yet.") }!!

  /**
   * 获取商品的定价历史
   * @param product 要查询的商品
   * @return 该商品的定价历史，按定价时间排序
   */
  fun pricingHistoryOf(product: Product):List<Pricing> = pricings.findPricingHistoryOfProduct(product).toList()
}
