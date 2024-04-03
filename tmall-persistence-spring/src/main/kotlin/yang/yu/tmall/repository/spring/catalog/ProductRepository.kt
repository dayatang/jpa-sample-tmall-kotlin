package yang.yu.tmall.repository.spring.catalog

import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.catalog.ProductCategory
import yang.yu.tmall.domain.catalog.Products
import yang.yu.tmall.repository.spring.AbstractRepository
import java.util.*
import java.util.stream.Stream

/**
 * 商品仓储的实现
 */
@Repository
interface ProductRepository : Products, AbstractRepository<Product> {
    override fun getByName(name: String): Optional<Product>

    override fun findByCategory(category: ProductCategory): Stream<Product>
}
