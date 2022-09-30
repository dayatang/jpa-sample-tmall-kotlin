package yang.yu.tmall.domain.commons

import java.math.BigDecimal
import java.math.RoundingMode
import javax.persistence.Embeddable

/**
 * 金额值对象。只保留两位小数
 */
@Embeddable
data class Money(val value: BigDecimal = BigDecimal.ZERO) {

  val scaledValue: BigDecimal
    get() = value.setScale(SCALE, RoundingMode.HALF_UP)

  operator fun plus(amount: Money): Money = Money(value.add(amount.value))

  operator fun minus(amount: Money): Money = Money(value.subtract(amount.value))

  operator fun times(amount: Int): Money = Money(value.multiply(BigDecimal(amount)))

  operator fun times(amount: Long): Money = Money(value.multiply(BigDecimal(amount)))

  operator fun times(amount: BigDecimal?): Money = if (amount == null) ZERO else Money(value.multiply(amount))

  operator fun times(amount: Double): Money = Money(value.multiply(BigDecimal(amount)))

  operator fun div(amount: Int): Money = Money(value.divide(BigDecimal(amount)))

  operator fun div(amount: Long): Money = Money(value.divide(BigDecimal(amount)))

  companion object {

    private const val SCALE = 2

    val ZERO = valueOf(0)

    /**
     * 从BigDecimal生成金额的工厂方法
     * @param amount 金额数量
     * @return 金额对象
     */
    fun valueOf(amount: BigDecimal): Money = Money(amount)

    /**
     * 从int生成金额的工厂方法
     * @param amount 金额数量
     * @return 金额对象
     */
    fun valueOf(amount: Int): Money = Money(BigDecimal.valueOf(amount.toLong()))

    /**
     * 从long生成金额的工厂方法
     * @param amount 金额数量
     * @return 金额对象
     */
    fun valueOf(amount: Long): Money = Money(BigDecimal.valueOf(amount))

    /**
     * 从double生成金额的工厂方法
     * @param amount 金额数量
     * @return 金额对象
     */
    fun valueOf(amount: Double): Money = Money(BigDecimal.valueOf(amount))

    /**
     * 从double生成金额的工厂方法
     * @param amount 金额数量
     * @return 金额对象
     */
    fun valueOf(amount: String): Money = Money(BigDecimal(amount))
  }
}
