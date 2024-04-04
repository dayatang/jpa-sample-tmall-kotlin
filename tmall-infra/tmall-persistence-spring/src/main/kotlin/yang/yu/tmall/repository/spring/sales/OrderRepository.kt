package yang.yu.tmall.repository.spring.sales

import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.sales.Order
import yang.yu.tmall.domain.sales.Orders
import yang.yu.tmall.repository.spring.AbstractRepository

/**
 * 订单仓储的实现
 */
@Repository
interface OrderRepository : Orders, AbstractRepository<Order>, OrderRepositoryExt
