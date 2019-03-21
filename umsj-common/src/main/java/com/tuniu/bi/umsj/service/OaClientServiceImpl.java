package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

/**
 * Oa相关服务类
 *
 * @author zhangwei21
 */
@Service
public class OaClientServiceImpl implements OaClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OaClientServiceImpl.class);

    @Value("${ldap.context.factory}")
    private String factory;

    @Value("${ldap.provider.url}")
    private String providerUrl;

    @Value("${ldap.security.authentication}")
    private String authentication;

    @Value("${ldap.security.principal.suffix}")
    private String suffix;

    /**
     * ldap认证查询的属性
     */
    private static final String[] LDAP_ATTRS = {"displayName", "mail", "department", "mobile", "sAMAccountName"};

    @Override
    public void checkOaAccount(String username, String password) throws AbstractException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
        env.put(Context.PROVIDER_URL, providerUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username + "@tuniu-inc.com");
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            // 初始化上下文
            InitialDirContext dc = new InitialDirContext(env);

            // 域节点
            String searchBase = "DC=tuniu-inc,DC=com";
            String searchFilter = "sAMAccountName=" + username;
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            //设置查询的属性,根据登陆用户姓名获取ou,定制返回属性
            searchCtls.setReturningAttributes(LDAP_ATTRS);

            // 根据设置的域节点、过滤器类和搜索控制器搜索LDAP得到结果
            NamingEnumeration<SearchResult> entries = dc.search(searchBase, searchFilter, searchCtls);
            SearchResult entry = entries.next();
            Attributes attrs = entry.getAttributes();
            String name = attrs.get("sAMAccountName").toString();
            if (StringUtils.isEmpty(name)) {
                throw new CommonException("认证异常!");
            }
        } catch (javax.naming.AuthenticationException e) {
            LOGGER.error("用户[" + username + "]认证失败!" + e.getMessage());
            throw new CommonException("认证失败!账户名或密码错误!");
        } catch (Exception e) {
            LOGGER.error("用户[" + username + "]认证出错!" + e.getMessage());
            throw new CommonException("认证出错!系统异常!");
        }
    }
}
