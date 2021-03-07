package yang.yu.tmall.repository.jpa

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import java.util.*
import java.util.stream.Stream
import javax.persistence.EntityManager

class BuyerRepositoryJpql(private val entityManager: EntityManager) : Buyers {
    override fun <T : Buyer> save(buyer: T): T {
        return entityManager.merge(buyer)
    }

    override fun delete(buyer: Buyer) {
        entityManager.remove(buyer)
    }

    override fun findAll(): List<Buyer> {
        return entityManager.createQuery("select o from Buyer o", Buyer::class.java).resultList
    }

    override fun getById(id: Int): Optional<Buyer> {
        return Optional.ofNullable(entityManager.find(Buyer::class.java, id))
    }

    override fun getByName(name: String): Optional<Buyer> {
        return entityManager
            .createQuery("select o from Buyer o where o.name = :name", Buyer::class.java)
            .setParameter("name", name)
            .resultStream
            .findAny()
    }

    override fun findByNameStartsWith(nameFragment: String): Stream<Buyer> {
        return entityManager
            .createQuery("select o from Buyer o where o.name Like :name", Buyer::class.java)
            .setParameter("name", "$nameFragment%")
            .resultStream
    }

    override fun findByNameContains(nameFragment: String): Stream<Buyer> {
        return entityManager
            .createQuery("select o from Buyer o where o.name Like :name", Buyer::class.java)
            .setParameter("name", "%$nameFragment%")
            .resultStream
    }

    override fun findPersonalBuyerByQQ(qq: String): Optional<PersonalBuyer> {
        val jpql = "select o from PersonalBuyer o join o.imInfos i where KEY(i) = :key and VALUE(i) = :value"
        return entityManager.createQuery(jpql, PersonalBuyer::class.java)
            .setParameter("key", ImType.QQ)
            .setParameter("value", qq)
            .resultStream
            .findAny()
    }
}