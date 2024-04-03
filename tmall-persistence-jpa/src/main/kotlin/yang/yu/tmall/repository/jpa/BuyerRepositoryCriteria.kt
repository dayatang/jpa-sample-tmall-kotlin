package yang.yu.tmall.repository.jpa

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.buyers.PersonalBuyer
import java.util.*
import java.util.stream.Stream
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery

class BuyerRepositoryCriteria(private val entityManager: EntityManager) :
  AbstractRepository<Buyer>(entityManager, Buyer::class.java), Buyers {

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
        get() = entityManager.criteriaBuilder

    private fun <T : Buyer> createCriteriaQuery(resultClass: Class<T>): CriteriaQuery<T> {
        return criteriaBuilder.createQuery(resultClass)
    }
}
