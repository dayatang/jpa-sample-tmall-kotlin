package yang.yu.tmall.domain.buyers

import jakarta.persistence.*
import yang.yu.lang.IoC
import yang.yu.tmall.domain.commons.Address
import yang.yu.tmall.domain.commons.BaseEntity
import java.util.*

@Entity
@Table(name = "buyers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
abstract class Buyer(
  @Column(nullable = false, unique = true)
  open val name: String
) : BaseEntity() {

  @Column(name = "mobile_no")
  var mobileNo: String? = null

  @Column(name = "wired_no")
  var wiredNo: String? = null

  var email: String? = null

  @ElementCollection
  @CollectionTable(name = "shipping_addresses", joinColumns = [JoinColumn(name = "buyer_id")])
  var shippingAddresses: MutableSet<Address> = HashSet()
    get() {
      return HashSet(field)
    }
    set(value) {
      field = HashSet(value)
    }

  fun addShippingAddress(address: Address) {
    shippingAddresses.add(address)
  }

  fun removeShippingAddress(address: Address) {
    shippingAddresses.remove(address)
  }

  fun save() = buyers.save(this)


  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other !is Buyer) {
      return false
    }
    return name == other.name
  }

  override fun hashCode(): Int {
    return Objects.hash(name)
  }

  override fun toString(): String = name

  companion object {
    private val buyers: Buyers
      get() = IoC.getInstance(Buyers::class.java)
  }
}
