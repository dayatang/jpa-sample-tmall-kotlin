package yang.yu.tmall.repository.spring.buyers

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.repository.spring.AbstractRepository
import java.util.*
import java.util.stream.Stream

/**
 * 买家仓储实现类。
 */
@Repository
interface BuyerRepository : Buyers, AbstractRepository<Buyer> {

  override fun getByName(name: String): Optional<Buyer>

  override fun findByNameStartsWith(nameFragment: String): Stream<Buyer>

  override fun findByNameContains(nameFragment: String): Stream<Buyer>

  @Query("select o from PersonalBuyer o join o.imInfos i where KEY(i) = :key and VALUE(i) = :value")
  fun findPersonalBuyerByImInfo(@Param("key") key: ImType, @Param("value") value: String): Optional<PersonalBuyer>


  override fun findPersonalBuyerByQQ(qq: String): Optional<PersonalBuyer> = findPersonalBuyerByImInfo(ImType.QQ, qq)

}
