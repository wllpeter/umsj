package com.tuniu.bi.umsj.config;

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

import javax.sql.DataSource;


/**
 * 基础数据源配置
 *
 * @author zhangwei21
 */
@Configuration
@MapperScan(basePackages = "com.tuniu.bi.umsj.mapper", sqlSessionFactoryRef = "baseSqlSessionFactory")
public class BaseDataSourceConfig {

    /**
     * 创建数据源
     *
     * @return
     */
    @Bean(name = "baseDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.base")
    public DataSource getDateSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建sqlSessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory baseSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    /**
     * 创建SqlSessionTemplate
     *
     * @param sessionFactory
     * @return
     */
    @Bean("baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate baseSqlSessionTemplate(
            @Qualifier("baseSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

}
