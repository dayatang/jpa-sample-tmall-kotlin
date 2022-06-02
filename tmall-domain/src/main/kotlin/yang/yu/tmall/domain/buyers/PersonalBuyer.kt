package yang.yu.tmall.domain.buyers

import java.util.*
import javax.persistence.*

@Entity
@DiscriminatorValue("P")
data class PersonalBuyer(override var name: String) : Buyer(name) {

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    @ElementCollection
    @CollectionTable(name = "contact_infos", joinColumns = [JoinColumn(name = "buyer_id")])
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "im_type")
    @Column(name = "im_value")
    var imInfos: MutableMap<ImType, String> = HashMap()

    fun getImInfo(type: ImType): String {
        return imInfos.getOrDefault(type, "")
    }

    fun setImInfo(type: ImType, value: String) {
        imInfos[type] = value
    }
}
