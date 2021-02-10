package yang.yu.tmall.domain.products

import java.util.*
import java.util.stream.Stream

interface Products {
    fun getById(id: Int): Optional<Product?>?
    fun getByName(name: String?): Optional<Product?>?
    fun findByCategory(category: ProductCategory?): Stream<Product?>?
}