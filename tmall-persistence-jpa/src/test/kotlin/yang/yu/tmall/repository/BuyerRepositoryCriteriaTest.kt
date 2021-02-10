package yang.yu.tmall.repository

import yang.yu.tmall.domain.commons.Money.Companion.valueOf
import yang.yu.tmall.domain.sales.Order.orderNo
import yang.yu.tmall.domain.sales.Order.buyer
import yang.yu.tmall.domain.sales.Orders.getById
import yang.yu.tmall.domain.commons.BaseEntity.id
import yang.yu.tmall.domain.sales.Orders.getByOrderNo
import yang.yu.tmall.domain.sales.Orders.findByBuyer
import yang.yu.tmall.domain.buyers.Buyer.equals
import yang.yu.tmall.domain.sales.Orders.findByProduct
import yang.yu.tmall.domain.sales.Orders.findByOrgBuyers
import yang.yu.tmall.domain.buyers.Buyers.save
import yang.yu.tmall.domain.buyers.Buyers.findAll
import yang.yu.tmall.domain.buyers.Buyers.getById
import yang.yu.tmall.domain.buyers.Buyers.getByName
import yang.yu.tmall.domain.buyers.Buyers.findByNameStartsWith
import yang.yu.tmall.domain.buyers.Buyers.findByNameContains
import yang.yu.tmall.domain.buyers.Buyers.delete
import yang.yu.tmall.domain.buyers.Buyer.name
import yang.yu.tmall.domain.buyers.PersonalBuyer.setImInfo
import yang.yu.tmall.domain.buyers.Buyers.findPersonalBuyerByQQ
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.*
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import yang.yu.tmall.repository.BaseIntegrationTest
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.domain.sales.OrderLine
import yang.yu.tmall.domain.products.Product
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.buyers.OrgBuyer
import yang.yu.tmall.domain.commons.Money
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.repository.jpa.BuyerRepositoryJpql
import yang.yu.tmall.repository.BuyerRepositoryJpqlTest
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.repository.jpa.BuyerRepositoryCriteria
import yang.yu.tmall.repository.BuyerRepositoryCriteriaTest
import java.util.function.Consumer

internal class BuyerRepositoryCriteriaTest : BaseIntegrationTest() {
    private var buyers: Buyers? = null
    private var buyer1: PersonalBuyer? = null
    private var buyer2: OrgBuyer? = null
    @BeforeEach
    fun beforeEach() {
        buyers = BuyerRepositoryCriteria(entityManager!!)
        buyer1 = buyers.save(PersonalBuyer(buyer1Name))
        buyer2 = buyers.save(OrgBuyer(buyer2Name))
    }

    @AfterEach
    fun afterEach() {
        buyers!!.findAll()!!.forEach(Consumer { buyer: Buyer? -> buyers!!.delete(buyer) })
    }

    @Test
    fun findById() {
        assertThat(buyers!!.getById(buyer1!!.id)).containsSame(buyer1)
        assertThat(buyers!!.getById(buyer2!!.id)).containsSame(buyer2)
    }

    @Test
    fun findByName() {
        assertThat(buyers!!.getByName(buyer1Name)).containsSame(buyer1)
        assertThat(buyers!!.getByName(buyer2Name)).containsSame(buyer2)
    }

    @Test
    fun findByNameStartsWith() {
        assertThat(buyers!!.findByNameStartsWith("华"))
            .contains(buyer2)
            .doesNotContain(buyer1)
        assertThat(buyers!!.findByNameStartsWith("三"))
            .isEmpty()
    }

    @Test
    fun findByNameContains() {
        assertThat(buyers!!.findByNameContains("三"))
            .contains(buyer1)
            .doesNotContain(buyer2)
    }

    @Test
    fun findAll() {
        assertThat(buyers!!.findAll()).contains(buyer1, buyer2)
    }

    @Test
    fun delete() {
        buyers!!.delete(buyer1)
        assertThat(buyers!!.findAll()).contains(buyer2).doesNotContain(buyer1)
    }

    @Test
    fun update() {
        buyer1!!.name = "李四"
        buyers!!.save(buyer1)
        assertThat(buyers!!.getById(buyer1!!.id)!!.map<String>(Buyer::name)).containsSame("李四")
        assertThat(buyers!!.getById(buyer2!!.id)!!.map<String>(Buyer::name)).containsSame(buyer2Name)
    }

    @Test
    fun findPersonalBuyerByQQ() {
        buyer1!!.setImInfo(ImType.QQ, "34567")
        buyers!!.save(buyer1)
        assertThat(buyers!!.findPersonalBuyerByQQ("34567")).containsSame(buyer1)
    }

    companion object {
        private const val buyer1Name = "张三"
        private const val buyer2Name = "华为公司"
    }
}