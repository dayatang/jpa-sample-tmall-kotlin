package yang.yu.tmall.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.buyers.*
import yang.yu.tmall.repository.jpa.BuyerRepositoryJpql
import java.util.function.Consumer

internal class BuyerRepositoryJpqlTest : BaseIntegrationTest() {
    private var buyers: Buyers? = null
    private var buyer1: PersonalBuyer? = null
    private var buyer2: OrgBuyer? = null
    @BeforeEach
    fun beforeEach() {
        buyers = BuyerRepositoryJpql(entityManager!!)
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