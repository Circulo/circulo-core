package com.circulo.service;

import org.apache.http.client.config.RequestConfig;

/**
 * Created by azim on 8/11/15.
 */
abstract public class AbstractVerificationService implements VerificationService {

    protected static final int MAX_REDIRECTS = 50;

    //@TODO : Fetch from properties
    //@Value("${circulo.core.verification.request-timeout}")
    protected int requestTimeout = 5000; // request timeout in ms

    protected RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setStaleConnectionCheckEnabled(true)
                .setRedirectsEnabled(true)
                .setMaxRedirects(MAX_REDIRECTS)
                .setRelativeRedirectsAllowed(true)
                .setAuthenticationEnabled(true)
                .setConnectionRequestTimeout(requestTimeout)
                .setConnectTimeout(requestTimeout)
                .setSocketTimeout(requestTimeout)
                .build();
    }
}
