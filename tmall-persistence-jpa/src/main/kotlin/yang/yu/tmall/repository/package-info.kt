/**
 * 实体仓储接口的技术实现
 */
package yang.yu.tmall.repository

import javax.persistence.EntityManager
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.domain.buyers.Buyer
import yang.yu.tmall.domain.products.Product
import java.time.LocalDateTime
import yang.yu.tmall.domain.buyers.Buyers
import yang.yu.tmall.domain.buyers.PersonalBuyer
import yang.yu.tmall.domain.buyers.ImType
import javax.persistence.criteria.CriteriaBuilder
