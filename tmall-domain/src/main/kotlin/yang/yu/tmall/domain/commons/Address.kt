package yang.yu.tmall.domain.commons

import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * 地址值对象
 */
@Embeddable
data class Address(
    val province: String,
    val city: String,
    val detail: String,
    val receiver: String,
    @Column(name = "receiver_phone")
    val receiverPhone: String)
