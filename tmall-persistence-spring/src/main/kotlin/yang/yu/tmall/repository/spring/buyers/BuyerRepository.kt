package yang.yu.tmall.repository.spring.buyers

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.repository.spring.AbstractRepository
import java.util.*
import java.util.stream.Stream
import jakarta.inject.Named

/**
 * 买家仓储实现类。
 */
@Named
class BuyerRepository(private val jpa: BuyerJpa) : Buyers, AbstractRepository<Buyer>(jpa) {
    override fun getByName(name: String): Optional<Buyer> = jpa.getByName(name)

    override fun findByNameStartsWith(nameFragment: String): Stream<Buyer> {
        println("=============$nameFragment")
        return jpa.findByNameStartsWith(nameFragment)
    }

    override fun findByNameContains(nameFragment: String): Stream<Buyer> = jpa.findByNameContains(nameFragment)

    override fun findPersonalBuyerByQQ(qq: String): Optional<PersonalBuyer> = jpa.findPersonalBuyerByImInfo(ImType.QQ, qq)

}
