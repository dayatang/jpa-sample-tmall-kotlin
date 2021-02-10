/**
 * 领域模型中的基础类型
 */
package yang.yu.tmall.domain.commons

import yang.yu.tmall.domain.commons.InstanceProvider
import yang.yu.tmall.domain.commons.IoC
import javax.persistence.Embeddable
import java.math.BigDecimal
import yang.yu.tmall.domain.commons.Money
import javax.persistence.GeneratedValue
import java.time.LocalDateTime
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.PostLoad
import java.lang.RuntimeException
import yang.yu.tmall.domain.commons.IocException
