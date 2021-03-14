package yang.yu.tmall.domain.commons

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 所有实体的共同基类。定义实体公共属性和行为
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract class BaseEntity : Serializable {
    /**
     * 获取ID
     * @return
     */
    /**
     * 设置ID
     * @param id 要设置的ID
     */
    @Id
    @GeneratedValue
    open var id = 0
    /**
     * 获取版本号。该属性用于辅助实现乐观锁
     * @return
     */
    /**
     * 设置版本号
     * @param version 要设置的版本号
     */
    @Version
    open var version = 0

    /**
     * 获取实体创建时间
     * @return 实体的创建时间
     */
    open var created: LocalDateTime = LocalDateTime.now()

    /**
     * 获取实体最后一次修改时间
     * @return 实体的最后修改时间
     */
    @Column(name = "last_updated")
    open var lastUpdated: LocalDateTime = LocalDateTime.now()

    /**
     * 判断实体是不是全新的（在数据库中没有对应记录）
     * @return 如果实体是新的，返回true；否则返回false
     */
    @Transient
    open var isNew = true

    /**
     * 生命周期回调方法。在实体将被第一次保存到数据库中前调用
     */
    @PrePersist
    fun beforeCreate() {
        isNew = false
        created = LocalDateTime.now()
    }

    /**
     * 生命周期回调方法。在实体每次保存到数据库中前调用
     */
    @PreUpdate
    fun beforeSave() {
        isNew = false
        lastUpdated = LocalDateTime.now()
    }

    /**
     * 生命周期回调方法。在实体每次从数据库装载后调用
     */
    @PostLoad
    fun afterLoad() {
        isNew = false
    }
}
