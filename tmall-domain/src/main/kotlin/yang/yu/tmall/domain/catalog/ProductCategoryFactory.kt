package yang.yu.tmall.domain.catalog

import jakarta.inject.Named

@Named
class ProductCategoryFactory {

  fun createRoot(name: String): ProductCategory = ProductCategory(name)

  fun createChild(name: String, parent: ProductCategory): ProductCategory = ProductCategory(name, parent)

}
