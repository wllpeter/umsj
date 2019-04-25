package com.tuniu.bi.umsj.base.config;

import com.tuniu.bi.umsj.base.annotation.ClusterMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 集群数据源配置
 *
 * @author zhangwei21
 */
@Configuration
@MapperScan(basePackages = {"com.tuniu.bi.umsj"}, sqlSessionFactoryRef = "clusterSqlSessionFactory", annotationClass = ClusterMapper.class)
public class ClusterDataSourceConfig {

    /**
     * 创建数据源
     *
     * @return
     */
    @Bean(name = "clusterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster")
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
    @Bean("clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager(@Qualifier("clusterDataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }
    /**
     * 创建sqlSessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource dataSource)
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
    @Bean("clusterSqlSessionTemplate")
    public SqlSessionTemplate clusterSqlSessionTemplate(
            @Qualifier("clusterSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
