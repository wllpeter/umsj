package com.tuniu.bi.umsj.base.config;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * 基础数据源配置
 *
 * @author zhangwei21
 */
@Configuration
@MapperScan(basePackages = {"com.tuniu.bi.umsj"}, sqlSessionFactoryRef = "umsSqlSessionFactory", annotationClass = UmsMapper.class)
public class UmsDataSourceConfig {

    /**
     * 创建数据源
     *
     * @return
     */
    @Bean(name = "umsDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.ums")
    public DataSource getDateSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理
     *
     * @param dataSource
     * @return
     * @throws SQLException
     */
    @Bean("umsTransactionManager")
    @Primary
    public DataSourceTransactionManager umsTransactionManager(@Qualifier("umsDataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }
    /**
     * 创建sqlSessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "umsSqlSessionFactory")
    @Primary
    public SqlSessionFactory umsSqlSessionFactory(@Qualifier("umsDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/***/mapper/*.xml"));
        return bean.getObject();
    }

    /**
     * 创建SqlSessionTemplate
     *
     * @param sessionFactory
     * @return
     */
    @Bean("umsSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate umsSqlSessionTemplate(
            @Qualifier("umsSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

}
