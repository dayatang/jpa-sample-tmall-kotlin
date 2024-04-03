package yang.yu.tmall.domain.commons

import java.util.*

interface BaseRepository<T : BaseEntity> {
  fun <S : T> save(entity: S): S

  fun <S : T> saveAll(entities: Iterable<S>): Iterable<S>

  fun deleteById(id: Int)

  fun delete(entity: T)

  fun deleteAllById(ids: Iterable<Int>)

  fun deleteAll(entities: Iterable<T>)

  fun deleteAll()

  fun findById(id: Int): Optional<T>

  fun existsById(id: Int): Boolean

  fun findAll(): Iterable<T>

  fun findAllById(ids: Iterable<Int>): Iterable<T>

  fun count(): Long
}
