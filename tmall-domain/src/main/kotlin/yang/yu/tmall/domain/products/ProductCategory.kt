package yang.yu.tmall.domain.products

import yang.yu.tmall.domain.commons.BaseEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "product_categories")
open class ProductCategory : BaseEntity {

    open var name: String? = null

    @ManyToOne
    open var parent: ProductCategory? = null

    @OneToMany(mappedBy = "parent")
    open val children: MutableSet<ProductCategory> = HashSet()
      get() = HashSet(field)

    constructor() {}

    constructor(name: String?) {
        this.name = name
    }

    constructor(name: String, parent: ProductCategory?) {
        this.name = name
        this.parent = parent
    }

    fun addChild(child: ProductCategory) {
        child.parent = this
        children.add(child)
    }

    fun removeChild(child: ProductCategory) {
        child.parent = null
        children.remove(child)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is ProductCategory) {
            return false
        }
        val that = o
        return name == that.name &&
                parent == that.parent
    }

    override fun hashCode(): Int {
        return Objects.hash(name, parent)
    }

    override fun toString(): String {
        return "ProductCategory{" +
                "name='" + name + '\'' +
                '}'
    }
}
