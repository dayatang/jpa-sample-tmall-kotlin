package yang.yu.tmall.domain.buyers

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class ContactInfo(
    var name: String,
    @Enumerated(EnumType.STRING) var gender: Gender = Gender.MALE,
    var mobileNo: String,
    var email: String)
