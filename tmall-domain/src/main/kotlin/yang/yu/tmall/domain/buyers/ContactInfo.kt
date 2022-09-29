package yang.yu.tmall.domain.buyers

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class ContactInfo(
    val name: String,

    @Enumerated(EnumType.STRING)
    val gender: Gender = Gender.MALE,

    val mobileNo: String? = null,

    val email: String? = null)
