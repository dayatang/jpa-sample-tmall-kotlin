package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

abstract class BaseIntegrationTest : WithAssertions {
    protected var entityManager: EntityManager? = null
    private var transaction: EntityTransaction? = null
    @BeforeEach
    fun BeforeEachTest() {
        entityManager = emf!!.createEntityManager()
        transaction = entityManager.getTransaction()
        transaction.begin()
        println("parent beforeEach")
    }

    @AfterEach
    fun afterEachTest() {
        transaction!!.rollback()
        entityManager!!.clear()
    }

    companion object {
        private var emf: EntityManagerFactory? = null
        @BeforeAll
        fun beforeAllTest() {
            emf = Persistence.createEntityManagerFactory("default")
        }

        @AfterAll
        fun afterAllTest() {
            emf!!.close()
        }
    }
}