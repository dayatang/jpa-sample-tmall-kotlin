package yang.yu.tmall.repository.spring.buyers

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import java.util.*
import java.util.stream.Stream
import javax.inject.Named

/**
 * 买家仓储实现类。
 */
@Named
interface BuyerJpa : JpaRepository<Buyer, Int> {

    fun getByName(name: String): Optional<Buyer>

    fun findByNameStartsWith(nameFragment: String): Stream<Buyer>

    fun findByNameContains(nameFragment: String): Stream<Buyer>

    @Query("select o from PersonalBuyer o join o.imInfos i where KEY(i) = :key and VALUE(i) = :value")
    fun findPersonalBuyerByImInfo(@Param("key") key: ImType, @Param("value") value: String): Optional<PersonalBuyer>
}
