package yang.yu.tmall.domain.catalog

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import yang.yu.tmall.domain.commons.BaseEntity

@Entity
@Table(name = "product_categories")
data class ProductCategory(
  val name: String,

  @ManyToOne
  val parent: ProductCategory? = null
) : BaseEntity() {

  @OneToMany(mappedBy = "parent")
  val children: MutableSet<ProductCategory> = HashSet()
    get() = HashSet(field)

  fun addChild(name: String) {
    children.add(ProductCategory(name, this))
  }

  fun removeChild(child: ProductCategory) {
    children.remove(child)
  }

  override fun toString(): String = name
}
