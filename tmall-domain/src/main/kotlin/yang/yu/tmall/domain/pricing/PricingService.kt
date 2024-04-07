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
class PricingService(
  private val pricings: Pricings,
  private val priceQueryService: PriceQueryService
) {

  /**
   * 为单个产品设定价格
   * @param product 要设定价格的商品
   * @param unitPrice 要设置的单价
   * @param effectiveInstant 生效时间
   * @return 一个新的定价对象
   */
  fun setPrice(product: Product, unitPrice: BigDecimal, effectiveInstant: Instant = Instant.now()): Pricing {
    return pricings.save(Pricing(product, unitPrice, effectiveInstant))
  }

  /**
   * 按百分比对单个产品调整价格
   * @param product 要设定价格的商品
   * @param percentage 要调整的单价的百分比。例如10就是价格上调10%，-5就是下调5%
   * @param effectiveInstant 调价生效时间
   * @return 一个新的定价对象
   */
  fun adjustPriceByPercentage(
    product: Product,
    percentage: Number,
    effectiveInstant: Instant = Instant.now()
  ): Pricing {
    val origPrice = priceQueryService.priceOfProduct(product)
    val newPrice = origPrice * (BigDecimal.valueOf(100) + percentage) / 100
    return setPrice(product, newPrice, effectiveInstant)
  }

  /**
   * 按百分比对一批产品调整价格
   * @param products 要设定价格的商品
   * @param percentage 要调整的单价的百分比。例如10就是价格上调10%，-5就是下调5%
   * @param effectiveInstant 调价生效时间
   * @return 一批定价对象
   */
  fun adjustPriceByPercentage(
    products: Collection<Product>,
    percentage: Number,
    effectiveInstant: Instant = Instant.now()
  ): Set<Pricing> =
    products.map { adjustPriceByPercentage(it, percentage, effectiveInstant) }.toSet()

}
