package yang.yu.tmall.repository.spring.catalog

import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.catalog.ProductCategories
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.repository.spring.AbstractRepository

@Repository
interface ProductCategoryRepository : ProductCategories, AbstractRepository<ProductCategory> {

  fun findByParentIsNull(): List<ProductCategory>

  override fun listAllRoot(): List<ProductCategory> = findByParentIsNull()

  fun findByParent_Id(categoryId: Int): List<ProductCategory>

  override fun listChildrenOf(categoryId: Int): List<ProductCategory> = findByParent_Id(categoryId)

  override fun createRootCategory(name: String): ProductCategory = save(ProductCategory(name))

  override fun createChildCategoryUnderParent(name: String, parentId: Int): ProductCategory =
    save(ProductCategory(name, getReferenceById(parentId)))

  override fun createChildCategoryUnderParent(name: String, parent: ProductCategory): ProductCategory =
    save(ProductCategory(name, parent))
}
