package yang.yu.tmall.repository.spring.products

import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.domain.catalog.Products
import yang.yu.tmall.repository.spring.AbstractRepository
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 商品仓储的实现
 */
@Named
class ProductRepository(private val jpa: ProductJpa) : Products, AbstractRepository<Product>(jpa) {
    override fun getByName(name: String): Optional<Product> = jpa.getByName(name)

    override fun findByCategory(category: ProductCategory): Stream<Product> = jpa.findByCategory(category)
}
