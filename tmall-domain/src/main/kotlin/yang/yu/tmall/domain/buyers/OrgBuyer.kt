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
@DiscriminatorValue("O")
class OrgBuyer : Buyer {
    @Column(name = "business_license_no")
    var businessLicenseNo: String? = null
    var taxNo: String? = null

    @AttributeOverrides(
        AttributeOverride(name = "name", column = Column(name = "contact_name")),
        AttributeOverride(name = "gender", column = Column(name = "contact_gender")),
        AttributeOverride(name = "mobileNo", column = Column(name = "contact_mobile_no")),
        AttributeOverride(name = "email", column = Column(name = "contact_email"))
    )
    var contactInfo: ContactInfo? = null

    constructor() {}
    constructor(name: String?) : super(name) {}
}