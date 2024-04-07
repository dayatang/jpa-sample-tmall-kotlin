package yang.yu.tmall.spring

import com.mchange.v2.c3p0.ComboPooledDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@ComponentScan("yang.yu.tmall")
@EnableJpaRepositories(basePackages = ["yang.yu.tmall.repository"])
@EnableTransactionManagement
@PropertySource("/jdbc.properties")
class JpaSpringConfig(private val env: Environment) {
  @Bean(destroyMethod = "close")
  @Throws(Exception::class)
  fun dataSource(): ComboPooledDataSource {
    val result = ComboPooledDataSource()
    result.driverClass = env.getProperty("jdbc.driverClassName")
    result.jdbcUrl = env.getProperty("jdbc.url")
    result.user = env.getProperty("jdbc.username")
    result.password = env.getProperty("jdbc.password", "")
    return result
  }

  @Bean
  fun jpaVendorAdapter(): JpaVendorAdapter {
    val result = HibernateJpaVendorAdapter()
    result.setDatabase(Database.valueOf(env.getProperty("db.type", "H2")))
    result.setDatabasePlatform(env.getProperty("hibernate.dialect"))
    result.setGenerateDdl(true)
    result.setShowSql(true)
    return result
  }

  @Bean
  fun entityManagerFactory(dataSource: DataSource, adapter: JpaVendorAdapter): LocalContainerEntityManagerFactoryBean {
    val result = LocalContainerEntityManagerFactoryBean()
    result.dataSource = dataSource
    result.jpaVendorAdapter = adapter
    result.setPackagesToScan("yang.yu.tmall.domain")
    result.jpaPropertyMap = hibernateProperties()
    return result
  }

  private fun hibernateProperties(): Map<String, String> =
    mapOf(Pair("hibernate.implicit_naming_strategy", "jpa"))

  @Bean
  fun transactionManager(entityManagerFactory: EntityManagerFactory): JpaTransactionManager {
    return JpaTransactionManager(entityManagerFactory)
  }
}
