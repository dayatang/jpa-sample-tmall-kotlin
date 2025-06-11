package yang.yu.tmall.domain.catalog

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import yang.yu.tmall.domain.commons.BaseEntity

/**
 * 商品类别
 */
@Entity
@Table(name = "product_categories")
data class ProductCategory(

  /**
   * 名称
   */
  val name: String,

  /**
   * 父类别
   */
  @ManyToOne
  val parent: ProductCategory? = null
) : BaseEntity() {

  /**
   * 子类别
   */
  @OneToMany(mappedBy = "parent")
  val children: MutableSet<ProductCategory> = HashSet()
    get() = HashSet(field)

  /**
   * 添加子类别
   */
  fun addChild(name: String) {
    children.add(ProductCategory(name, this))
  }

  /**
   * 删除子类别
   */
  fun removeChild(child: ProductCategory) {
    children.remove(child)
  }

  override fun toString(): String = name
}
