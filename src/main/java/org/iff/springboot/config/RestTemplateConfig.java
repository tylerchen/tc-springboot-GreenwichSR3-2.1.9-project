/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;

/**
 * RestTemplateConfig
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 绕过验证
     *
     * @return
     */
    public static SSLContext createIgnoreVerifySSL() {
        try {
            SSLContext sc = SSLContext.getInstance("SSLv3");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager() {
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sc.init(null, new TrustManager[]{trustManager}, null);
            return sc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HostnameVerifier createHostnameVerifier() {
        HostnameVerifier hv = (a, b) -> {
            return true;
        };
        return hv;
    }

    @ConditionalOnProperty(name = "config.rest-template.load-balance.enable", havingValue = "false", matchIfMissing = true)
    @Configuration
    public class SimpleRestTemplate {
        @Value("${config.rest-template.ssl.enable:false}")
        boolean sslEnable;

        @Bean
        RestTemplate restTemplate() {
            if (!sslEnable) {
                return new RestTemplate();
            }
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(), createHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            return new RestTemplate(requestFactory);
        }
    }

    @ConditionalOnProperty(name = "config.rest-template.load-balance.enable", havingValue = "true")
    @Configuration
    public class LoadBalancedRestTemplate {
        @Value("${config.rest-template.ssl.enable:false}")
        boolean sslEnable;

        @Bean
        @LoadBalanced
        RestTemplate restTemplate() {
            if (!sslEnable) {
                return new RestTemplate();
            }
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(), createHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            return new RestTemplate(requestFactory);
        }
    }
}
