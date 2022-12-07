package yang.yu.tmall.repository

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import yang.yu.tmall.domain.buyers.*
import yang.yu.tmall.spring.JpaSpringConfig
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@SpringJUnitConfig(classes = [JpaSpringConfig::class])
@Transactional
open class BuyerRepositoryTest : WithAssertions {
    @Inject
    private lateinit var buyers: Buyers
    private lateinit var buyer1: PersonalBuyer
    private lateinit var buyer2: OrgBuyer

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
        assertThat(buyers.getById(buyer1.id)).containsSame(buyer1)
        assertThat(buyers.getById(buyer2.id)).containsSame(buyer2)
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
//        assertThat(buyers.findByNameStartsWith("ABC"))
//            .doesNotContain(buyer1)
//            .doesNotContain(buyer2)
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
        buyer1.email = "me@my.com"
        buyers.save(buyer1)
        assertThat(buyers.getById(buyer1.id).map(Buyer::email)).containsSame("me@my.com")
        assertThat(buyers.getById(buyer2.id).map(Buyer::name)).containsSame(buyer2Name)
    }

    companion object {
        private const val buyer1Name = "张三"
        private const val buyer2Name = "华为公司"
    }
}