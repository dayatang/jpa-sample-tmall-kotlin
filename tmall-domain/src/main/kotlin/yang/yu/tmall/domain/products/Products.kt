package yang.yu.tmall.domain.products

import yang.yu.tmall.domain.commons.BaseRepository
import java.util.*
import java.util.stream.Stream

interface Products: BaseRepository<Product> {
    fun getByName(name: String): Optional<Product>
    fun findByCategory(category: ProductCategory): Stream<Product>
}