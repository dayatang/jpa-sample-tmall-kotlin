package yang.yu.lang

import java.util.concurrent.TimeUnit

/**
 * 分布式锁接口
 */
interface DistributedLock {

  /**
   * 使用指定超时时间配合Redis锁实现业务操作（带出参）
   *
   * @param key 键值
   * @param supplier 带出参的业务操作
   * @param time 超时时间
   * @param timeUnit 时间类型
   * @return 业务响应
   * @param <T> 出参数据类型
  </T> */
  fun <T> callInLock(
    key: String,
    supplier: () -> T,
    time: Long = DEFAULT_WAIT_TIME,
    timeUnit: TimeUnit = TimeUnit.SECONDS
  ): T

  /**
   * 使用指定超时时间配合Redis锁实现业务操作（不带出参）
   *
   * @param key 键值
   * @param consumer 不带出参的业务操作
   * @param time 超时时间
   * @param timeUnit 时间类型
   */
  fun runInLock(
    key: String,
    consumer: () -> Unit,
    time: Long = DEFAULT_WAIT_TIME,
    timeUnit: TimeUnit = TimeUnit.SECONDS
  )

  companion object {
    const val DEFAULT_WAIT_TIME: Long = 10
  }
}
