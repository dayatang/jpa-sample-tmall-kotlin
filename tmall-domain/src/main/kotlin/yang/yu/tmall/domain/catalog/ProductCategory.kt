package yang.yu.tmall.domain.catalog

import yang.yu.tmall.domain.commons.BaseEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

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
}
