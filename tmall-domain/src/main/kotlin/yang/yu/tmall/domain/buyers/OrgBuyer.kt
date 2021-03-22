package yang.yu.tmall.domain.buyers

import javax.persistence.*

@Entity
@DiscriminatorValue("O")
open class OrgBuyer(name: String) : Buyer(name) {

    @Column(name = "business_license_no")
    open var businessLicenseNo: String? = null

    open var taxNo: String? = null

    @AttributeOverrides(
        AttributeOverride(name = "name", column = Column(name = "contact_name")),
        AttributeOverride(name = "gender", column = Column(name = "contact_gender")),
        AttributeOverride(name = "mobileNo", column = Column(name = "contact_mobile_no")),
        AttributeOverride(name = "email", column = Column(name = "contact_email"))
    )
    open var contactInfo: ContactInfo? = null

}
