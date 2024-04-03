package yang.yu.tmall.repository.jpa

import jakarta.persistence.EntityManager
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.BaseRepository
import java.util.*

abstract class AbstractRepository<T: BaseEntity>(
  private val entityManager: EntityManager,
  private val clazz: Class<T>
): BaseRepository<T> {
  override fun <S : T> save(entity: S): S = entityManager.merge(entity)

  override fun <S : T> saveAll(entities: Iterable<S>): Iterable<S> = entities.map(entityManager::merge)

  override fun deleteById(id: Int) = entityManager.remove(entityManager.getReference(clazz, id))

  override fun deleteAllById(ids: Iterable<Int>) = ids.forEach(::deleteById)

  override fun deleteAll() = findAll().forEach(entityManager::remove)

  override fun findById(id: Int): Optional<T> = Optional.ofNullable(entityManager.find(clazz, id))

  override fun existsById(id: Int): Boolean = findById(id).isPresent

  override fun findAll(): Iterable<T> {
    val query = entityManager.criteriaBuilder.createQuery(clazz)
    return entityManager.createQuery(query.select(query.from(clazz))).resultList
  }

  override fun findAllById(ids: Iterable<Int>): Iterable<T> = ids.map { findById(it).orElseThrow() }

  override fun count(): Long = findAll().count().toLong()

  override fun deleteAll(entities: Iterable<T>) = findAll().forEach(entityManager::remove)

  override fun delete(entity: T) = entityManager.remove(entity)

}
