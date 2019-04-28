package com.tuniu.bi.umsj.base.service;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.base.constant.Symbol;
import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.entitydo.UcQuerySalesRequestDO;
import com.tuniu.bi.umsj.base.entitydo.UcQuerySalesResponseDO;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

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

    @Value("${rest.oa.url}")
    private String oaUrl;

    @Value("${rest.oa.key}")
    private String oaKey;

    @Value("${rest.oa.system}")
    private String oaSystem;



    /**
     * 查询员工信息
     */
    private static final String SERVICE_QUERY_SALES = "query_sales";
    /**
     * 查询部门信息
     */
    private static final String SERVICE_QUERY_DEPTS = "query_depts";

    /**
     * 根据拼音查询
     */
    private static final int UC_SALES_QUERY_TYPE_PINYIN_NAME = 4;
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
            throw new CommonException("认证失败!账户名或密码错误!", e);
        } catch (Exception e) {
            throw new CommonException("认证出错!系统异常!", e);
        }
    }

    @Override
    public UserEntity getUser(String username) throws AbstractException {
        String url = oaUrl;
        UcQuerySalesRequestDO ucQuerySalesRequestDO = new UcQuerySalesRequestDO();
        ucQuerySalesRequestDO.setKey(oaKey);
        ucQuerySalesRequestDO.setSubSystem(oaSystem);
        UcQuerySalesRequestDO.Data data = new UcQuerySalesRequestDO.Data();
        UcQuerySalesRequestDO.Condition condition = new UcQuerySalesRequestDO.Condition();
        condition.setType(UC_SALES_QUERY_TYPE_PINYIN_NAME);
        condition.setValue(username);
        data.setService(SERVICE_QUERY_SALES);
        data.setCond(Arrays.asList(condition));
        ucQuerySalesRequestDO.setData(data);

        byte[] bytes = JSONObject.toJSONBytes(ucQuerySalesRequestDO);
        String param = Base64.encodeBase64String(bytes);
        if (url.indexOf(Symbol.QUESTION_MARK) != -1) {
            url = url.substring(0, url.indexOf(Symbol.QUESTION_MARK));
        }
        url = url + "?" + param;
        OkHttpClient client = new OkHttpClient();
        Request build = new Request.Builder().url(url).build();
        Response response;
        UcQuerySalesResponseDO ucQuerySalesResponseDO;
        UserEntity userEntity = null;
        try {
            response = client.newCall(build).execute();
            if (!response.isSuccessful()) {
                //  查询不成功
                throw new CommonException("查询不到员工信息");
            }
            // base64解密
            byte[] bytes1 = Base64.decodeBase64(response.body().bytes());
            ucQuerySalesResponseDO = JSONObject.parseObject(bytes1, UcQuerySalesResponseDO.class);
        } catch (IOException e) {
            throw new CommonException("访问oa接口异常", e);
        }
        if (!ucQuerySalesResponseDO.getSuccess()) {
            throw new CommonException(ucQuerySalesResponseDO.getMsg());
        }
        if (ucQuerySalesResponseDO != null && ucQuerySalesResponseDO.getData() != null) {
            Map<String, UcQuerySalesResponseDO.Employees> sales = ucQuerySalesResponseDO.getData().getSales();
            if (!CollectionUtils.isEmpty(sales)) {
                UcQuerySalesResponseDO.Employees employee = sales.get(username);
                if (employee != null) {
                    userEntity = new UserEntity();
                    userEntity.setUsername(username);
                    userEntity.setDepartment(employee.getDept());
                    userEntity.setWorkNo(employee.getWorkNum());
                    userEntity.setFullName(employee.getName());
                    userEntity.setPhone(employee.getCellPhone());
                    userEntity.setSalerId(employee.getSalerId());
                }
            }
        }
        return userEntity;
    }
}
