package yang.yu.tmall.domain.products

import yang.yu.tmall.domain.commons.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(var name: String,
                   @ManyToOne var category: ProductCategory
) : BaseEntity()
