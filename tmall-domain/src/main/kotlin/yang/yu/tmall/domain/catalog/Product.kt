package yang.yu.tmall.domain.catalog

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import yang.yu.tmall.domain.commons.BaseEntity

/**
 * 商品
 */
@Entity
@Table(name = "products")
data class Product(

  /**
   * 名称
   */
  val name: String,

  /**
   * 类别
   */
  @ManyToOne
  val category: ProductCategory
) : BaseEntity() {
  override fun toString(): String = name
}
