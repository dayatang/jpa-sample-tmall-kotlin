package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import yang.yu.tmall.domain.buyers.*
import javax.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
class BuyerRepositoryTest : WithAssertions {
    @Inject
    private val buyers: Buyers? = null
    private var buyer1: PersonalBuyer? = null
    private var buyer2: OrgBuyer? = null
    @BeforeEach
    fun beforeEach() {
        buyer1 = buyers.save(PersonalBuyer(buyer1Name))
        buyer2 = buyers.save(OrgBuyer(buyer2Name))
    }

    @AfterEach
    fun afterEach() {
        buyers.findAll().forEach(buyers::delete)
    }

    @Test
    fun findById() {
        assertThat(buyers.getById(buyer1.getId())).containsSame(buyer1)
        assertThat(buyers.getById(buyer2.getId())).containsSame(buyer2)
    }

    @Test
    fun findByName() {
        assertThat(buyers.getByName(buyer1Name)).containsSame(buyer1)
        assertThat(buyers.getByName(buyer2Name)).containsSame(buyer2)
    }

    @Test
    fun findByNameStartsWith() {
        assertThat(buyers.findByNameStartsWith("华"))
                .contains(buyer2)
                .doesNotContain(buyer1)
        assertThat(buyers.findByNameStartsWith("三"))
                .isEmpty()
    }

    @Test
    fun findByNameContains() {
        assertThat(buyers.findByNameContains("三"))
                .contains(buyer1)
                .doesNotContain(buyer2)
    }

    @Test
    fun findAll() {
        assertThat(buyers.findAll()).contains(buyer1, buyer2)
    }

    @Test
    fun findPersonalBuyerByQQ() {
        buyer1.setImInfo(ImType.QQ, "34567")
        buyers.save(buyer1)
        assertThat(buyers.findPersonalBuyerByQQ("34567")).containsSame(buyer1)
    }

    @Test
    fun delete() {
        buyers.delete(buyer1)
        assertThat(buyers.findAll()).contains(buyer2).doesNotContain(buyer1)
    }

    @Test
    fun update() {
        buyer1.setName("李四")
        buyers.save(buyer1)
        assertThat(buyers.getById(buyer1.getId()).map(Buyer::getName)).containsSame("李四")
        assertThat(buyers.getById(buyer2.getId()).map(Buyer::getName)).containsSame(buyer2Name)
    }

    companion object {
        private const val buyer1Name = "张三"
        private const val buyer2Name = "华为公司"
    }
}