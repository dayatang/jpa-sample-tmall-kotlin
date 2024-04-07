package yang.yu.tmall.domain.buyers

import jakarta.inject.Named
import java.util.*

@Named
class BuyerFactory {

  fun createPersonalBuyer(
    name: String,
    gender: Gender = Gender.MALE,
    imInfos: MutableMap<ImType, String> = EnumMap(ImType::class.java)
  ): Buyer {
    return PersonalBuyer(name, gender, imInfos)
  }

  fun createOrgBuyer(name: String): Buyer {
    return OrgBuyer(name)
  }
}
