package com.github.kyleryxn.snapsnatch.crawler.http;

import com.github.kyleryxn.snapsnatch.common.LoggingConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HttpClientFactory {
    private final int maxConnectionsTotal;
    private final int maxConnectionsPerRoute;
    private final PoolingHttpClientConnectionManager connectionManager;

    public HttpClientFactory() {
        LoggingConfig.configure(HttpClientFactory.class); // Prevent console output of logs
        maxConnectionsTotal = 50;
        maxConnectionsPerRoute = 50;
        connectionManager = createConnectionManager();
    }

    private PoolingHttpClientConnectionManager createConnectionManager() {
        SocketConfig socketConfig = createSocketConfig();

        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultSocketConfig(socketConfig)
                .setConnPoolPolicy(PoolReusePolicy.FIFO)
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setDefaultTlsConfig(TlsConfig.custom()
                        .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
                        .build())
                .setMaxConnTotal(maxConnectionsTotal)
                .setMaxConnPerRoute(maxConnectionsPerRoute)
                .build();
    }

    private SocketConfig createSocketConfig() {
        return SocketConfig.custom()
                .setSoTimeout(Timeout.ofMilliseconds(5000))
                .build();
    }

    @Bean("httpClient")
    public CloseableHttpClient createCustom() {
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }

}