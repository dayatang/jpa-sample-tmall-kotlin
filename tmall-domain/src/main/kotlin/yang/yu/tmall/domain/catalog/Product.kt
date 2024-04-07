package yang.yu.tmall.domain.catalog

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import yang.yu.tmall.domain.commons.BaseEntity

@Entity
@Table(name = "products")
data class Product(
  val name: String,

  @ManyToOne
  val category: ProductCategory
) : BaseEntity() {
  override fun toString(): String = name
}
