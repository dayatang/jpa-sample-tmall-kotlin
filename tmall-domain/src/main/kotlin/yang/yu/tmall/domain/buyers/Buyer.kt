package yang.yu.tmall.domain.buyers

import yang.yu.tmall.domain.commons.BaseEntity
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.buyers.ContactInfo
import yang.yu.tmall.domain.buyers.Gender
import yang.yu.tmall.domain.buyers.ImType
import yang.yu.tmall.domain.commons.Address
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "buyers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
abstract class Buyer : BaseEntity {
    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    var name: String? = null

    @Column(name = "mobile_no")
    var mobileNo: String? = null

    @Column(name = "wired_no")
    var wiredNo: String? = null
    var email: String? = null

    @ElementCollection
    @CollectionTable(name = "shipping_addresses", joinColumns = [JoinColumn(name = "buyer_id")])
    private val shippingAddresses: MutableSet<Address> = HashSet()

    protected constructor() {}
    constructor(name: String?) {
        this.name = name
    }

    fun getShippingAddresses(): Set<Address> {
        return HashSet(shippingAddresses)
    }

    fun addShippingAddresses(address: Address) {
        shippingAddresses.add(address)
    }

    fun removeShippingAddresses(address: Address) {
        shippingAddresses.remove(address)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Buyer) {
            return false
        }
        return name == o.name
    }

    override fun hashCode(): Int {
        return Objects.hash(name)
    }

    override fun toString(): String {
        return "Buyer{" +
                "name='" + name + '\'' +
                '}'
    }
}