package de.effective;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author martin.dilger
 */
@Configuration
@ImportResource(value = "classpath:de/effective/spring-context.xml")
@ComponentScan(basePackages = "de.effective")
public class ApplicationConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaDialect(new HibernateJpaDialect());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPersistenceUnitManager(null);
        emf.setPackagesToScan("de.effective");
        return emf;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setDatabase(Database.HSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){
          return new JpaTransactionManager(emf);
    }
}
