package yang.yu.tmall.domain.pricing

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.BaseEntity
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "pricings")
data class Pricing(
  @ManyToOne
  val product: Product,

  @Column(name = "unit_price")
  val unitPrice: BigDecimal = BigDecimal.ZERO,

  @Column(name = "effective_instant")
  val effectiveInstant: Instant = Instant.now()
) : BaseEntity()
