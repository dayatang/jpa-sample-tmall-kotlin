package yang.yu.tmall.domain.buyers

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class ContactInfo(
    var name: String,
    @Enumerated(EnumType.STRING) var gender: Gender = Gender.MALE,
    var mobileNo: String,
    var email: String)
