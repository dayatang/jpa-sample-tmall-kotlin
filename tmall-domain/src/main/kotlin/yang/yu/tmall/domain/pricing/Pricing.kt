package yang.yu.tmall.domain.pricing

import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import java.time.LocalDateTime
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

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
