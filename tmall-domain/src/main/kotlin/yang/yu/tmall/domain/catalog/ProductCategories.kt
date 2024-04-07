package yang.yu.tmall.domain.catalog

import yang.yu.tmall.domain.commons.BaseRepository

interface ProductCategories: BaseRepository<ProductCategory> {

  fun listAllRoot(): List<ProductCategory>

  fun listChildrenOf(categoryId: Int): List<ProductCategory>

  fun listProductOf(categoryId: Int): List<Product>

  fun createRootCategory(name: String): ProductCategory

  fun createChildCategoryUnderParent(name: String, parentId: Int): ProductCategory

  fun createChildCategoryUnderParent(name: String, parent: ProductCategory): ProductCategory

}
