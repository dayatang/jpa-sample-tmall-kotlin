package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import java.time.LocalDateTime
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "pricings")
data class Pricing(
  @ManyToOne
  val product: Product,

  @AttributeOverride(name = "value", column = Column(name = "unit_price"))
  val unitPrice: Money = Money.ZERO,

  @Column(name = "effective_time")
  val effectiveTime: LocalDateTime = LocalDateTime.now()
) : BaseEntity()
