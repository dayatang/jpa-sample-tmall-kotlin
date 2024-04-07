package yang.yu.tmall.domain.buyers

import jakarta.persistence.*

@Entity
@DiscriminatorValue("O")
class OrgBuyer(name: String) : Buyer(name) {

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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is OrgBuyer) {
      return false
    }
    return super.equals(other)
  }

}
