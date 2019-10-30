/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.*;

/**
 * ShiroConfig
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Slf4j
@ConditionalOnProperty(name = "config.web.enable", havingValue = "true")
@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    @DependsOn({"securityManager"})
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        log.info("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/captcha", "anon");
        filterChainDefinitionMap.put("/admin/code/**", "anon");
        filterChainDefinitionMap.put("admin/**/page-query", "user");
        filterChainDefinitionMap.put("/admin/employee/logout", "logout");
        filterChainDefinitionMap.put("admin/**/detail", "authc");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /*shiroFilterFactoryBean.setU("/403");*/
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * Shiro生命周期处理器
     * <p>
     * /*1.LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }


    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 安全管理器：securityManager
     *
     * @return
     */
    @Bean(name = "shiroEhCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager shiroEhCacheManager(CacheManager cacheManager) {
        log.info("ShiroConfiguration.getEhCacheManager()");
        return new EhCacheManager(cacheManager);
    }

    @Bean(name = "adminRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public SimpleAccountRealm adminRealm(EhCacheManager shiroEhCacheManager) {
        SimpleAccountRealm realm = new SimpleAccountRealm();
        //为确保密码安全，可以定义hash算法，（此处未做任何hash，直接用密码匹配）
        /*HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-1");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        adminRealm.setCredentialsMatcher(matcher);*/
        realm.setCacheManager(shiroEhCacheManager);
        return realm;
    }

    /**
     * 设置rememberMe  Cookie 7天
     *
     * @return
     */
    @Bean(name = "simpleCookie")
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(30 * 60);
        return simpleCookie;
    }

    /**
     * cookie 管理器
     *
     * @return
     */
    @Bean(name = "cookieRememberMeManager")
    @DependsOn({"simpleCookie"})
    public CookieRememberMeManager getCookieRememberMeManager(SimpleCookie simpleCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        /**
         * 设置 rememberMe cookie 的密钥 ，不设置 很可能：javax.crypto.BadPaddingException: Given final block not properly padded
         */
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * @param realm
     * @param shiroEhCacheManager
     * @param cookieRememberMeManager
     * @return
     * @DependOn :在初始化 defaultWebSecurityManager 实例前 强制先初始化 adminRealm ，shiroEhCacheManager。。。。。
     */

    @Bean(name = "securityManager")
    @DependsOn({"adminRealm", "shiroEhCacheManager", "cookieRememberMeManager"})
    public DefaultWebSecurityManager getDefaultWebSecurityManager(SimpleAccountRealm realm, EhCacheManager shiroEhCacheManager, CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置realm.
        defaultWebSecurityManager.setRealm(realm);
        defaultWebSecurityManager.setCacheManager(shiroEhCacheManager);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 * 配置以下两个bean
     * (DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * 由Advisor决定对哪些类的方法进行AOP代理。
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        defaultAAP.setUsePrefix(true);
        return defaultAAP;
    }

    @Bean
    @DependsOn("securityManager")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Slf4j
    public static class EhCache<K, V> implements Cache<K, V> {

        private org.springframework.cache.Cache cache;

        public EhCache(org.springframework.cache.Cache cache) {
            this.cache = cache;
        }

        public V get(K key) throws CacheException {
            try {
                if (log.isTraceEnabled()) {
                    log.trace("Getting object from cache [" + cache.getName() + "] for key [" + key + "]");
                }
                if (key == null) {
                    return null;
                } else {
                    V value = (V) cache.get(key).get();
                    if (value == null) {
                        if (log.isTraceEnabled()) {
                            log.trace("Element for [" + key + "] is null.");
                        }
                        return null;
                    } else {
                        //noinspection unchecked
                        return value;
                    }
                }
            } catch (Throwable t) {
                throw new CacheException(t);
            }
        }

        public V put(K key, V value) throws CacheException {
            if (log.isTraceEnabled()) {
                log.trace("Putting object in cache [" + cache.getName() + "] for key [" + key + "]");
            }
            try {
                V previous = get(key);
                cache.put(key, value);
                return previous;
            } catch (Throwable t) {
                throw new CacheException(t);
            }
        }

        public V remove(K key) throws CacheException {
            if (log.isTraceEnabled()) {
                log.trace("Removing object from cache [" + cache.getName() + "] for key [" + key + "]");
            }
            try {
                V previous = get(key);
                cache.evict(key);
                return previous;
            } catch (Throwable t) {
                throw new CacheException(t);
            }
        }

        public void clear() throws CacheException {
            if (log.isTraceEnabled()) {
                log.trace("Clearing all objects from cache [" + cache.getName() + "]");
            }
            try {
                cache.clear();
            } catch (Throwable t) {
                throw new CacheException(t);
            }
        }

        public int size() {
            log.warn("This operation not support in ehcache 3.");
            return -1;
        }

        public Set<K> keys() {
//            try {
//                @SuppressWarnings({"unchecked"})
//                List<K> keys = cache.getKeys();
//                if (!isEmpty(keys)) {
//                    return Collections.unmodifiableSet(new LinkedHashSet<K>(keys));
//                } else {
//                    return Collections.emptySet();
//                }
//            } catch (Throwable t) {
//                throw new CacheException(t);
//            }
            log.warn("This operation not support in ehcache 3.");
            return Collections.emptySet();
        }

        public Collection<V> values() {
//            try {
//                @SuppressWarnings({"unchecked"})
//                List<K> keys = cache.getKeys();
//                if (!isEmpty(keys)) {
//                    List<V> values = new ArrayList<V>(keys.size());
//                    for (K key : keys) {
//                        V value = get(key);
//                        if (value != null) {
//                            values.add(value);
//                        }
//                    }
//                    return Collections.unmodifiableList(values);
//                } else {
//                    return Collections.emptyList();
//                }
//            } catch (Throwable t) {
//                throw new CacheException(t);
//            }
            log.warn("This operation not support in ehcache 3.");
            return Collections.emptyList();
        }
    }

    public static class EhCacheManager implements org.apache.shiro.cache.CacheManager, Initializable, Destroyable {

        private org.springframework.cache.CacheManager cacheManager;

        public EhCacheManager(org.springframework.cache.CacheManager cacheManager) {
            this.cacheManager = cacheManager;
        }

        public <K, V> Cache<K, V> getCache(String s) throws CacheException {
            return new EhCache<>(cacheManager.getCache(s));
        }

        public void destroy() throws Exception {
            // to nothing, management by spring cache
        }

        public void init() throws ShiroException {
            // do nothing, init by spring cache
        }
    }
}
