package yang.yu.tmall.repository.spring.orders

import org.springframework.stereotype.Repository
import yang.yu.tmall.domain.orders.Order
import yang.yu.tmall.domain.orders.Orders
import yang.yu.tmall.repository.spring.AbstractRepository

/**
 * 订单仓储的实现
 */
@Repository
interface OrderRepository : Orders, AbstractRepository<Order>, OrderRepositoryExt
