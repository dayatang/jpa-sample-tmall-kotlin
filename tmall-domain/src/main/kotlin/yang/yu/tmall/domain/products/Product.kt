package yang.yu.tmall.domain.products

import yang.yu.tmall.domain.commons.BaseEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "products")
open class Product : BaseEntity {
    var name: String? = null

    @ManyToOne
    var category: ProductCategory? = null

    constructor() {}
    constructor(name: String?, category: ProductCategory?) {
        this.name = name
        this.category = category
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Product) {
            return false
        }
        val product = o
        return name == product.name &&
                category == product.category
    }

    override fun hashCode(): Int {
        return Objects.hash(name, category)
    }

    override fun toString(): String {
        return "Product{" +
                "name='" + name + '\'' +
                '}'
    }
}