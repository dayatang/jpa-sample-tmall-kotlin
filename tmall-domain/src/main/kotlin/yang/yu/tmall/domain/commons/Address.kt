package yang.yu.tmall.domain.commons

import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * 地址值对象
 */
@Embeddable
open class Address(
    val province: String,
    val city: String,
    val detail: String,
    val receiver: String,
    @Column(name = "receiver_phone")
    val receiverPhone: String){

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Address) {
            return false
        }
        return province == o.province &&
                city == o.city &&
                detail == o.detail &&
                receiver == o.receiver &&
                receiverPhone == o.receiverPhone
    }

    override fun hashCode(): Int {
        return Objects.hash(province, city, detail, receiver, receiverPhone)
    }

    override fun toString(): String {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", detail='" + detail + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                '}'
    }
}
