package yang.yu.tmall.repository.spring.catalog

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.catalog.ProductCategory
import java.util.*
import java.util.stream.Stream
import jakarta.inject.Named

/**
 * 商品仓储的实现
 */
@Named
interface ProductJpa : JpaRepository<Product, Int> {

    fun getByName(name: String): Optional<Product>

    fun findByCategory(category: ProductCategory): Stream<Product>
}
