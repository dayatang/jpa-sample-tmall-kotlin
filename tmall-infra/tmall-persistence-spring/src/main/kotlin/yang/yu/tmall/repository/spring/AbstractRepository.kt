package yang.yu.tmall.repository.spring

import org.springframework.data.jpa.repository.JpaRepository
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.commons.BaseRepository

interface AbstractRepository<T : BaseEntity> : JpaRepository<T, Int>, BaseRepository<T>
