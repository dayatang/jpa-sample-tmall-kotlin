package yang.yu.tmall.domain.commons

import yang.yu.tmall.domain.commons.InstanceProvider
import yang.yu.tmall.domain.commons.IoC
import java.math.BigDecimal
import yang.yu.tmall.domain.commons.Money
import java.time.LocalDateTime
import java.lang.RuntimeException
import yang.yu.tmall.domain.commons.IocException
import java.util.*
import javax.persistence.*

/**
 * 地址值对象
 */
@Embeddable
class Address {
    var province: String? = null
    var city: String? = null
    var detail: String? = null
    var receiver: String? = null

    @Column(name = "receiver_phone")
    var receiverPhone: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Address) {
            return false
        }
        val address = o
        return province == address.province &&
                city == address.city &&
                detail == address.detail &&
                receiver == address.receiver &&
                receiverPhone == address.receiverPhone
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