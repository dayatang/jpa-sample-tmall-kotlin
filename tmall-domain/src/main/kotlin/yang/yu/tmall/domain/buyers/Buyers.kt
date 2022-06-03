package yang.yu.tmall.domain.buyers

import yang.yu.tmall.domain.commons.BaseRepository
import java.util.*
import java.util.stream.Stream

interface Buyers: BaseRepository<Buyer> {
    fun getByName(name: String): Optional<Buyer>
    fun findByNameStartsWith(nameFragment: String): Stream<Buyer>
    fun findByNameContains(nameFragment: String): Stream<Buyer>
    fun findPersonalBuyerByQQ(qq: String): Optional<PersonalBuyer>
}