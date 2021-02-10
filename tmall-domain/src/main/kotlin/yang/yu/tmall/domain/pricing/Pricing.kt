package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.commons.Money.multiply
import yang.yu.tmall.domain.commons.Money.divide
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.commons.Money
import java.time.LocalDateTime
import kotlin.jvm.JvmOverloads
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.Pricings
import yang.yu.tmall.domain.pricing.PricingException
import java.lang.RuntimeException
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "pricings")
class Pricing : BaseEntity {
    @ManyToOne
    var product //商品
            : Product? = null
        private set
    var unitPrice //单价
            : Money? = null
        private set

    @Column(name = "effective_time")
    var effectiveTime //生效时间
            : LocalDateTime? = null
        private set

    constructor() {}

    @JvmOverloads
    constructor(product: Product?, unitPrice: Money?, effectiveTime: LocalDateTime? = LocalDateTime.now()) {
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