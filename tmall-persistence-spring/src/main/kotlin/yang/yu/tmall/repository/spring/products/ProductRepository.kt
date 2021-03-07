package yang.yu.tmall.repository.spring.products

import yang.yu.tmall.domain.products.Product
import javax.inject.Named

/**
 * 商品仓储的实现
 */
@Named
interface ProductRepository : Products, JpaRepository<Product?, Int?>