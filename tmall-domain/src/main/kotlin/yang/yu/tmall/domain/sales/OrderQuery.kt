package yang.yu.tmall.domain.sales

import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.catalog.Product
import yang.yu.tmall.domain.commons.Money
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class OrderQuery {
  var id: Int? = null
  var buyer: Buyer? = null
  var buyerName: String? = null
  var isOrgBuyer: Boolean? = null
  var isPersonalBuyer: Boolean? = null
  var orderNo: String? = null
  var product: Product? = null
  var createdFrom: Instant? = null
  var createdUntil: Instant? = null
  var shipToProvince: String? = null
  var shipToCity: String? = null
  var receiverName: String? = null
  var receiverPhone: String? = null
  var totalPriceLessThan: Money? = null
  var totalPriceNotLessThan: Money? = null


  fun id(id: Int): OrderQuery {
    this.id = id
    return this
  }

  fun buyer(buyer: Buyer): OrderQuery {
    this.buyer = buyer
    return this
  }

  fun buyerName(buyerName: String): OrderQuery {
    this.buyerName = buyerName
    return this
  }

  fun isOrgBuyer(): OrderQuery {
    this.isOrgBuyer = true
    return this
  }

  fun isPersonalBuyer(): OrderQuery {
    this.isPersonalBuyer = true
    return this
  }

  fun orderNo(orderNo: String): OrderQuery {
    this.orderNo = orderNo
    return this
  }

  fun product(product: Product): OrderQuery {
    this.product = product
    return this
  }

  fun createdFrom(createdFrom: Instant): OrderQuery {
    this.createdFrom = createdFrom
    return this
  }

  fun createdUntil(createdUntil: Instant): OrderQuery {
    this.createdUntil = createdUntil
    return this
  }

  fun createdAt(createdAt: LocalDate): OrderQuery {
    this.createdFrom = createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant()
    this.createdUntil = createdAt.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
    return this
  }

  fun shipToProvince(shipToProvince: String): OrderQuery {
    this.shipToProvince = shipToProvince
    return this
  }

  fun shipToCity(shipToCity: String): OrderQuery {
    this.shipToCity = shipToCity
    return this
  }

  fun receiverName(receiverName: String): OrderQuery {
    this.receiverName = receiverName
    return this
  }

  fun receiverPhone(receiverPhone: String): OrderQuery {
    this.receiverPhone = receiverPhone
    return this
  }

  fun totalPriceLessThan(value: Money): OrderQuery {
    this.totalPriceLessThan = value
    return this
  }

  fun totalPriceNotLessThan(value: Money): OrderQuery {
    this.totalPriceNotLessThan = value
    return this
  }

  fun hasBuyerQuery(): Boolean =
    buyerName != null || isOrgBuyer != null || isPersonalBuyer != null

  fun hasLineItemQuery(): Boolean = product != null

  fun hasAddressQuery(): Boolean =
    shipToProvince != null || shipToCity != null || receiverName != null || receiverPhone != null


}
