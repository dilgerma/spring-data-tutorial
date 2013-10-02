package de.effectivetrainings.app;

import de.effectivetrainings.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@ImportResource(value = "classpath:de/effectivetrainings/app/spring-context.xml")
@EnableJpaRepositories(basePackages = "de.effectivetrainings")
public class ApplicationConfig {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaDialect(new HibernateJpaDialect());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPersistenceUnitManager(null);
        emf.setPackagesToScan("de.effectivetrainings");
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
