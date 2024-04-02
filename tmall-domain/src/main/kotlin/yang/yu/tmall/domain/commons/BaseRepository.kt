package yang.yu.tmall.domain.commons

import java.util.*

interface BaseRepository<T: BaseEntity> {
    fun <S: T> save(entity: S): S
    fun delete(entity: T)
    fun findAll(): List<T>
    fun getById(id: Int): Optional<T>
}