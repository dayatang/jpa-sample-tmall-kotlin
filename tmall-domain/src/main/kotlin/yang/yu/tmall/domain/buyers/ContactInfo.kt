package yang.yu.tmall.domain.buyers

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class ContactInfo(
    val name: String,

    @Enumerated(EnumType.STRING)
    val gender: Gender = Gender.MALE,

    val mobileNo: String? = null,

    val email: String? = null)
