package yang.yu.tmall.domain.catalog

import yang.yu.tmall.domain.commons.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "products")
data class Product(var name: String,
                   @ManyToOne var category: ProductCategory
) : BaseEntity()
