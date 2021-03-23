package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.products.Product
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "pricings")
data class Pricing(@ManyToOne var product: Product,
                   var unitPrice: Money = Money.ZERO,
                   @Column(name = "effective_time") var effectiveTime: LocalDateTime = LocalDateTime.now()
) : BaseEntity()
