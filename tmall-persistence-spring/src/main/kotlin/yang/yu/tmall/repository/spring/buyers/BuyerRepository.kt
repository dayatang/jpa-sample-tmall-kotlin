package yang.yu.tmall.repository.spring.buyers

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import java.util.*
import javax.inject.Named

/**
 * 买家仓储实现类。
 */
@Named
interface BuyerRepository : Buyers, JpaRepository<Buyer, Int> {


    override fun findPersonalBuyerByQQ(qq: String): Optional<PersonalBuyer> {
        return findPersonalBuyerByImInfo(ImType.QQ, qq)
    }

    @Query("select o from PersonalBuyer o join o.imInfos i where KEY(i) = :key and VALUE(i) = :value")
    fun findPersonalBuyerByImInfo(@Param("key") key: ImType, @Param("value") value: String): Optional<PersonalBuyer>
}
