package yang.yu.tmall.domain.buyers

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class ContactInfo {
    var name: String? = null

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null
    var mobileNo: String? = null
    var email: String? = null
}