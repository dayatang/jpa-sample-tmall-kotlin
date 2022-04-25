package yang.yu.tmall.domain.products

import yang.yu.tmall.domain.commons.BaseEntity
import java.util.*
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "product_categories")
data class ProductCategory(var name: String,
                           @ManyToOne var parent: ProductCategory? = null
) : BaseEntity() {

    @OneToMany(mappedBy = "parent")
    val children: MutableSet<ProductCategory> = HashSet()
      get() = HashSet(field)

    fun addChild(child: ProductCategory) {
        child.parent = this
        children.add(child)
    }

    fun removeChild(child: ProductCategory) {
        child.parent = null
        children.remove(child)
    }
}
