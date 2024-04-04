package yang.yu.tmall.domain.buyers

import java.util.*
import jakarta.persistence.*

@Entity
@DiscriminatorValue("P")
class PersonalBuyer(
  name: String,

  @Enumerated(EnumType.STRING)
  val gender: Gender = Gender.MALE,

  @ElementCollection
  @CollectionTable(name = "contact_infos", joinColumns = [JoinColumn(name = "buyer_id")])
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "im_type")
  @Column(name = "im_value")
  var imInfos: MutableMap<ImType, String> = EnumMap(ImType::class.java)
) : Buyer(name) {

  fun getImInfo(type: ImType): String {
    return imInfos.getOrDefault(type, "")
  }

  fun setImInfo(type: ImType, value: String) {
    imInfos[type] = value
  }


  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is PersonalBuyer) {
      return false
    }
    return super.equals(other)
  }

}
