package yang.yu.tmall.domain.catalog

import yang.yu.lang.IoC.getInstance
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.pricing.Pricing
import yang.yu.tmall.domain.pricing.PricingService
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    var name: String? = null,
    @ManyToOne
    var category: ProductCategory? = null
): BaseEntity() {


    constructor(): this(null, null)

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Product) {
            return false
        }
        val product = o
        return name == product.name && this.category == product.category
    }

    override fun hashCode(): Int {
        return Objects.hash(name, this.category)
    }

    override fun toString(): String {
        return "Product{" +
                "name='" + name + '\'' +
                '}'
    }
}
