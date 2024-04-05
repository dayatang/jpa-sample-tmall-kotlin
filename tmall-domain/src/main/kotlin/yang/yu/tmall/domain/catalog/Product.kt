package yang.yu.tmall.domain.catalog

import yang.yu.tmall.domain.commons.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    val name: String,

    @ManyToOne
    val category: ProductCategory? = null
): BaseEntity() {
  override fun toString(): String = name
}
