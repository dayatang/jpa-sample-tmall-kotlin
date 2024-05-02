package yang.yu.tmall.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import jakarta.persistence.Persistence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

abstract class BaseIntegrationTest : WithAssertions {

  protected lateinit var entityManager: EntityManager
  private lateinit var transaction: EntityTransaction

  @BeforeEach
  fun beforeEachTest() {
    entityManager = emf!!.createEntityManager()
    transaction = entityManager.transaction
    transaction.begin()
    println("parent beforeEach")
  }

  @AfterEach
  fun afterEachTest() {
    transaction.rollback()
    entityManager.clear()
  }

  companion object {
    var emf: EntityManagerFactory? = null

    @JvmStatic
    @BeforeAll
    fun beforeAllTest() {
      emf = Persistence.createEntityManagerFactory("default")
    }

    @JvmStatic
    @AfterAll
    fun afterAllTest() {
      emf!!.close()
    }
  }
}
