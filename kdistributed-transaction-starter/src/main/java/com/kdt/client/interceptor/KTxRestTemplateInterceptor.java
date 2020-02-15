package com.kdt.client.interceptor;

import com.kdt.client.util.TxManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class KTxRestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("kdist-tx-groupid", TxManager.getOrCreateGroupID());
        System.out.println("KTxRestTemplateInterceptor entered, groupID:" + TxManager.getOrCreateGroupID());
        return execution.execute(request, body);
    }
}
