package yang.yu.tmall.domain.commons

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.persistence.Embeddable

/**
 * 金额值对象。只保留两位小数
 */
@Embeddable
open class Money {
    /**
     * 获取金额值，以BigDecimal形式表示
     * @return 金额值
     */
    /**
     * 设置金额值
     * @param value 要设置的金额值
     */
    var value = BigDecimal.ZERO
        private set

    /**
     * 构造函数
     */
    constructor() {}

    /**
     * 构造函数。以BigDecimal为参数
     * @param amount 金额值
     */
    constructor(amount: BigDecimal?) {
        if (amount == null) {
            value = BigDecimal.ZERO
            return
        }
        value = amount.setScale(SCALE, RoundingMode.HALF_UP)
    }

    fun add(amount: Money): Money {
        return Money(value.add(amount.value))
    }

    fun subtract(amount: Money): Money {
        return Money(value.subtract(amount.value))
    }

    fun multiply(amount: Int): Money {
        return Money(value.multiply(BigDecimal(amount)))
    }

    fun multiply(amount: Long): Money {
        return Money(value.multiply(BigDecimal(amount)))
    }

    fun multiply(amount: BigDecimal?): Money {
        return if (amount == null) {
            ZERO
        } else Money(value.multiply(amount))
    }

    fun multiply(amount: Double): Money {
        return Money(value.multiply(BigDecimal(amount)))
    }

    fun divide(amount: Int): Money {
        return Money(value.divide(BigDecimal(amount)))
    }

    fun divide(amount: Long): Money {
        return Money(value.divide(BigDecimal(amount)))
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Money) {
            return false
        }
        return value == o.value
    }

    override fun hashCode(): Int {
        return Objects.hash(value)
    }

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        private const val SCALE = 2
        @JvmField
        val ZERO = valueOf(0)

        /**
         * 从BigDecimal生成金额的工厂方法
         * @param amount 金额数量
         * @return 金额对象
         */
        fun valueOf(amount: BigDecimal?): Money {
            return Money(amount)
        }

        /**
         * 从int生成金额的工厂方法
         * @param amount 金额数量
         * @return 金额对象
         */
        fun valueOf(amount: Int): Money {
            return Money(BigDecimal.valueOf(amount.toLong()))
        }

        /**
         * 从long生成金额的工厂方法
         * @param amount 金额数量
         * @return 金额对象
         */
        fun valueOf(amount: Long): Money {
            return Money(BigDecimal.valueOf(amount))
        }

        /**
         * 从double生成金额的工厂方法
         * @param amount 金额数量
         * @return 金额对象
         */
        fun valueOf(amount: Double): Money {
            return Money(BigDecimal.valueOf(amount))
        }

        /**
         * 从double生成金额的工厂方法
         * @param amount 金额数量
         * @return 金额对象
         */
        @JvmStatic
        fun valueOf(amount: String?): Any {
            return Money(BigDecimal(amount))
        }
    }
}