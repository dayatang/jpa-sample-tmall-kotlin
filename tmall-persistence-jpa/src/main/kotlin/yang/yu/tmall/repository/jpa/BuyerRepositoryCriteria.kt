package yang.yu.tmall.repository.jpa

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import java.util.*
import java.util.stream.Stream
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

class BuyerRepositoryCriteria(private val entityManager: EntityManager) : Buyers {
    override fun <T : Buyer> save(buyer: T): T {
        return entityManager.merge(buyer)
    }

    override fun delete(buyer: Buyer) {
        entityManager.remove(buyer)
    }

    override fun findAll(): List<Buyer> {
        val query = createCriteriaQuery(Buyer::class.java)
        return entityManager.createQuery(query.select(query.from(Buyer::class.java))).resultList
    }

    override fun getById(id: Int): Optional<Buyer> {
        return Optional.ofNullable(entityManager.find(Buyer::class.java, id))
    }

    override fun getByName(name: String): Optional<Buyer> {
        val query = createCriteriaQuery(Buyer::class.java)
        val root = query.from(Buyer::class.java)
        val predicate = criteriaBuilder.equal(root.get<Any>("name"), name)
        return entityManager
            .createQuery(query.select(root).where(predicate))
            .resultStream
            .findAny()
    }

    override fun findByNameStartsWith(nameFragment: String): Stream<Buyer> {
        val query = createCriteriaQuery(Buyer::class.java)
        val root = query.from(Buyer::class.java)
        val predicate = criteriaBuilder.like(root.get("name"), "$nameFragment%")
        return entityManager
            .createQuery(query.select(root).where(predicate))
            .resultStream
    }

    override fun findByNameContains(nameFragment: String): Stream<Buyer> {
        val query = createCriteriaQuery(Buyer::class.java)
        val root = query.from(Buyer::class.java)
        val predicate = criteriaBuilder.like(root.get("name"), "%$nameFragment%")
        return entityManager
            .createQuery(query.select(root).where(predicate))
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

    private val criteriaBuilder: CriteriaBuilder
        private get() = entityManager.criteriaBuilder

    private fun <T : Buyer?> createCriteriaQuery(resultClass: Class<T>): CriteriaQuery<T> {
        return criteriaBuilder.createQuery(resultClass)
    }
}
