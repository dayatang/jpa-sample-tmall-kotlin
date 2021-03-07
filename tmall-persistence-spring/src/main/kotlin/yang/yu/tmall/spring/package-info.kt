/**
 * Spring配置
 */
package yang.yu.tmall.spring

import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import kotlin.Throws
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import java.util.HashMap
import javax.persistence.EntityManagerFactory
import org.springframework.orm.jpa.JpaTransactionManager
