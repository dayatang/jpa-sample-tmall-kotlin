package yang.yu.tmall.domain.buyers

import java.util.*
import java.util.stream.Stream

interface Buyers {
    fun <T : Buyer?> save(buyer: T): T
    fun delete(buyer: Buyer?)
    fun findAll(): List<Buyer?>?
    fun getById(id: Int): Optional<Buyer?>?
    fun getByName(name: String?): Optional<Buyer?>?
    fun findByNameStartsWith(nameFragment: String?): Stream<Buyer?>?
    fun findByNameContains(nameFragment: String?): Stream<Buyer?>?
    fun findPersonalBuyerByQQ(qq: String?): Optional<PersonalBuyer?>?
}