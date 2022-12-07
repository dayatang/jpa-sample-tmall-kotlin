package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.Money
import java.time.LocalDateTime
import java.util.stream.Stream
import jakarta.inject.Named

/**
 * 定价领域服务
 * @param pricings 定价仓储
 */
@Named
class PricingService(private val pricings: Pricings) {

    /**
     * 为单个产品设定价格
     * @param product 要设定价格的商品
     * @param unitPrice 要设置的单价
     * @param effectiveTime 生效时间
     * @return 一个新的定价对象
     */
    fun setPrice(product: Product, unitPrice: Money, effectiveTime: LocalDateTime): Pricing {
        return pricings.save(Pricing(product, unitPrice, effectiveTime))
    }

    /**
     * 按百分比对单个产品调整价格
     * @param product 要设定价格的商品
     * @param percentage 要调整的单价的百分比。例如10就是价格上调10%，-5就是下调5%
     * @param effectiveTime 生效时间
     * @return 一个新的定价对象
     */
    fun adjustPriceByPercentage(product: Product, percentage: Number, effectiveTime: LocalDateTime): Pricing {
        val newPrice = currentPrice(product).times(percentage.toDouble() + 100).div(100)
        return setPrice(product, newPrice, effectiveTime)
    }

    /**
     * 按百分比对一批产品调整价格
     * @param products 要设定价格的商品
     * @param percentage 要调整的单价的百分比。例如10就是价格上调10%，-5就是下调5%
     * @param effectiveTime 生效时间
     * @return 一批定价对象
     */
    fun adjustPriceByPercentage(products: Set<Product>, percentage: Number, effectiveTime: LocalDateTime): Set<Pricing> {
        return products.map { adjustPriceByPercentage(it, percentage, effectiveTime) }.toSet()
    }

    /**
     * 获取特定时刻的商品价格
     * @param product 要查询价格的商品
     * @param time 要查询的时刻
     * @return 指定商品在指定时刻的单价
     * @throws PricingException 当商品还没设定单价时抛出此异常
     */
    fun priceAt(product: Product, time: LocalDateTime): Money {
        return pricings.getPricingAt(product, time)
                .map { it.unitPrice }
                .orElseThrow { PricingException(product.name + "'s price has not been set yet.") }!!
    }

    /**
     * 获取商品的当前价格
     * @param product 要查询价格的商品
     * @return 商品的当前价格
     */
    fun currentPrice(product: Product): Money {
        return priceAt(product, LocalDateTime.now())
    }

    /**
     * 获取商品的定价历史
     * @param product 要查询的商品
     * @return 该商品的定价历史，按定价时间排序
     */
    fun pricingHistory(product: Product): Stream<Pricing> {
        return pricings.findPricingHistoryOfProduct(product)
    }
}
