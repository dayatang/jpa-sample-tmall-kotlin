package yang.yu.lang.lock

import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import yang.yu.lang.DistributedLock
import yang.yu.lang.DistributedLock.Companion.DEFAULT_WAIT_TIME
import yang.yu.lang.LockAcquireTimeoutException
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import java.util.function.Supplier

//@Named
class RedissonLock(private val redissonClient: RedissonClient) : DistributedLock {
    /**
     * 使用指定超时时间配合Redis锁实现业务操作（带出参）
     *
     * @param key 键值
     * @param time 超时时间
     * @param timeUnit 时间类型
     * @param supplier 带出参的业务操作
     * @return 业务响应
     * @param <T> 出参数据类型
    </T> */
    override fun <T> callInLock(key: String, supplier: () -> T, time: Long, timeUnit: TimeUnit): T {
        val lock = redissonClient.getLock(key)
        try {
            //加锁，防止并发问题
            if (lock.tryLock(time, timeUnit)) {
                return supplier.invoke()
            } else {
                throw InterruptedException()
            }
        } catch (interruptedException: InterruptedException) {
            LOGGER.warn("获取锁失败【{}】", key, interruptedException)
            Thread.currentThread().interrupt()
            throw LockAcquireTimeoutException()
        } finally {
            try {
                // 持有锁的场合，释放锁
                if (lock.isLocked && lock.isHeldByCurrentThread) {
                    lock.unlock()
                }
            } catch (exception: Exception) {
                // 释放锁异常时不向上抛出异常，保证业务正常提交
                LOGGER.warn("释放锁异常【{}】", key, exception)
            }
        }
    }

    /**
     * 使用指定超时时间配合Redis锁实现业务操作（不带出参）
     *
     * @param key 键值
     * @param time 超时时间
     * @param timeUnit 时间类型
     * @param consumer 不带出参的业务操作
     */
    override fun runInLock(key: String, consumer: () -> Unit, time: Long, timeUnit: TimeUnit) {
        val lock = redissonClient.getLock(key)
        try {
            //加锁，防止并发问题
            if (lock.tryLock(time, timeUnit)) {
                consumer.invoke()
            } else {
                throw InterruptedException()
            }
        } catch (interruptedException: InterruptedException) {
            LOGGER.warn("获取锁失败【{}】", key, interruptedException)
            Thread.currentThread().interrupt()
            throw LockAcquireTimeoutException()
        } finally {
            try {
                // 持有锁的场合，释放锁
                if (lock.isLocked && lock.isHeldByCurrentThread) {
                    lock.unlock()
                }
            } catch (exception: Exception) {
                // 释放锁异常时不向上抛出异常，保证业务正常提交
                LOGGER.warn("释放锁异常【{}】", key, exception)
            }
        }
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(RedissonLock::class.java)
    }
}
