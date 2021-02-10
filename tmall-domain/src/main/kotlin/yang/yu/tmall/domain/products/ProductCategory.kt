package yang.yu.tmall.domain.products

import yang.yu.tmall.domain.commons.Money.multiply
import yang.yu.tmall.domain.commons.Money.divide
import yang.yu.tmall.domain.commons.Money.subtract
import yang.yu.tmall.domain.commons.IoC.getInstance
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.sales.DuplicateOrderLineException
import yang.yu.tmall.domain.products.Product
import java.util.function.BinaryOperator
import java.time.LocalDateTime
import java.math.BigDecimal
import kotlin.jvm.JvmOverloads
import yang.yu.tmall.domain.sales.OrderStatusTransitions
import yang.yu.tmall.domain.commons.IoC
import yang.yu.tmall.domain.sales.OrderStatusTransition
import yang.yu.tmall.domain.sales.OrderStatus
import java.util.stream.Collectors
import yang.yu.tmall.domain.sales.OrderLifecycle
import java.lang.RuntimeException
import yang.yu.tmall.domain.products.ProductCategory
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product_categories")
class ProductCategory : BaseEntity {
    var name: String? = null

    @ManyToOne
    var parent: ProductCategory? = null

    @OneToMany(mappedBy = "parent")
    private val children: MutableSet<ProductCategory> = HashSet()

    constructor() {}
    constructor(name: String?) {
        this.name = name
    }

    constructor(name: String?, parent: ProductCategory?) {
        this.name = name
        this.parent = parent
    }

    fun getChildren(): Set<ProductCategory> {
        return HashSet(children)
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