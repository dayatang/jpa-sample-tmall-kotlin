package yang.yu.tmall.domain.buyers

import yang.yu.tmall.domain.commons.BaseEntity
import java.util.HashSet
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.buyers.ContactInfo
import yang.yu.tmall.domain.buyers.Gender
import yang.yu.tmall.domain.buyers.ImType
import java.util.HashMap
import javax.persistence.*

@Entity
@DiscriminatorValue("P")
class PersonalBuyer : Buyer {
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    @ElementCollection
    @CollectionTable(name = "contact_infos", joinColumns = [JoinColumn(name = "buyer_id")])
    @MapKeyEnumerated(
        EnumType.STRING
    )
    @MapKeyColumn(name = "im_type")
    @Column(name = "im_value")
    private var imInfos: MutableMap<ImType, String> = HashMap()

    constructor() {}
    constructor(name: String?) : super(name) {}

    fun getImInfos(): Map<ImType, String> {
        return imInfos
    }

    fun setImInfos(imInfos: MutableMap<ImType, String>) {
        this.imInfos = imInfos
    }

    fun getImInfo(type: ImType): String {
        return imInfos.getOrDefault(type, "")
    }

    fun setImInfo(type: ImType, value: String) {
        imInfos[type] = value
    }
}