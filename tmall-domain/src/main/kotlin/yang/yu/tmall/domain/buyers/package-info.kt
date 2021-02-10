/**
 * 买家相关领域对象
 */
package yang.yu.tmall.domain.buyers

import javax.persistence.Inheritance
import javax.persistence.DiscriminatorColumn
import yang.yu.tmall.domain.commons.BaseEntity
import javax.persistence.ElementCollection
import javax.persistence.CollectionTable
import javax.persistence.JoinColumn
import java.util.HashSet
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.buyers.PersonalBuyer
import javax.persistence.DiscriminatorValue
import javax.persistence.AttributeOverrides
import javax.persistence.AttributeOverride
import yang.yu.tmall.domain.buyers.ContactInfo
import javax.persistence.Embeddable
import javax.persistence.Enumerated
import yang.yu.tmall.domain.buyers.Gender
import javax.persistence.MapKeyEnumerated
import javax.persistence.MapKeyColumn
import yang.yu.tmall.domain.buyers.ImType
import java.util.HashMap
