package yang.yu.tmall.domain.commons

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * 地址值对象
 */
@Embeddable
data class Address(

    val province: String,

    val city: String,

    val detail: String,

    val receiverName: String,

    val receiverPhone: String
)
