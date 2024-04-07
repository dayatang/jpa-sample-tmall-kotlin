package yang.yu.tmall.domain.catalog

import jakarta.inject.Named

@Named
class ProductFactory {
  fun create(name: String, category: ProductCategory): Product = Product(name, category)
}
