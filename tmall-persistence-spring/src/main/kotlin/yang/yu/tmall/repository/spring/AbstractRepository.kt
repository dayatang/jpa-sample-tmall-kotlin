package yang.yu.tmall.repository.spring

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.BaseRepository
import java.util.*

abstract class AbstractRepository<T: BaseEntity>(private val jpa: JpaRepository<T, Int>): BaseRepository<T> {

    override fun <S: T> save(entity: S): S = jpa.save(entity)

    override fun delete(entity: T) = jpa.delete(entity)

    override fun findAll(): List<T> = jpa.findAll()

    override fun getById(id: Int): Optional<T> = jpa.findById(id)

}