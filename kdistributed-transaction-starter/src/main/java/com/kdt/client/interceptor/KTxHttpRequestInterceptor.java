package com.kdt.client.interceptor;


import com.kdt.client.util.TxManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class KTxHttpRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String txGroupID = request.getHeader("kdist-tx-groupid");
        TxManager.setGroupID(txGroupID);
        return true;
    }
}
