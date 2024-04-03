package yang.yu.tmall.repository.spring

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.BaseRepository
import java.util.*

abstract class AbstractRepository<T: BaseEntity>(private val jpa: JpaRepository<T, Int>): BaseRepository<T> {
  override fun <S : T> save(entity: S): S = jpa.save(entity)

  override fun <S : T> saveAll(entities: Iterable<S>): Iterable<S> = jpa.saveAll(entities)

  override fun findById(id: Int): Optional<T> = jpa.findById(id)

  override fun existsById(id: Int): Boolean = jpa.existsById(id)

  override fun findAll(): Iterable<T> = jpa.findAll()

  override fun findAllById(ids: Iterable<Int>): Iterable<T> = jpa.findAllById(ids)

  override fun count(): Long = jpa.count()

  override fun deleteById(id: Int) = jpa.deleteById(id)

  override fun delete(entity: T) = jpa.delete(entity)

  override fun deleteAllById(ids: Iterable<Int>) = jpa.deleteAllById(ids)

  override fun deleteAll(entities: Iterable<T>) = jpa.deleteAll(entities)

  override fun deleteAll() = jpa.deleteAll()
}
