package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.products.Product
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "pricings")
open class Pricing : BaseEntity {

    //商品
    @ManyToOne
    var product: Product? = null

    //单价
    var unitPrice: Money = Money.ZERO

    //定价生效时间
    @Column(name = "effective_time")
    var effectiveTime: LocalDateTime = LocalDateTime.now()

    constructor() {}

    @JvmOverloads
    constructor(product: Product, unitPrice: Money = Money.ZERO, effectiveTime: LocalDateTime = LocalDateTime.now()) {
        this.product = product
        this.unitPrice = unitPrice
        this.effectiveTime = effectiveTime
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Pricing) {
            return false
        }
        val pricing = o
        return product == pricing.product && effectiveTime == pricing.effectiveTime
    }

    override fun hashCode(): Int {
        return Objects.hash(product, effectiveTime)
    }

    override fun toString(): String {
        return "Pricing{" +
                "product=" + product +
                ", unitPrice=" + unitPrice +
                ", pricingTime=" + effectiveTime +
                '}'
    }
}
