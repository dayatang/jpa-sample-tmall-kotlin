package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.commons.BaseRepository
import yang.yu.tmall.domain.catalogue.Product
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream

/**
 * 定价仓储接口
 */
interface Pricings: BaseRepository<Pricing> {

    /**
     * 获得指定商品在指定时间的定价信息
     * @param product 商品
     * @param time 时间
     * @return 当时的商品价格
     */
    fun getPricingAt(product: Product, time: LocalDateTime): Optional<Pricing>

    /**
     * 查找指定商品的定价历史，按生效时间排序。
     * @param product 商品
     * @return 指定商品按时间排序的定价历史列表
     */
    fun findPricingHistoryOfProduct(product: Product): Stream<Pricing>
}
