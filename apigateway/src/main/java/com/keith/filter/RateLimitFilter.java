package com.keith.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.keith.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
*   @Description: 限流
*
*   @Author: SK-Keith
*   @Date: 2020/8/2
*/
public class RateLimitFilter extends ZuulFilter {

    private static final RateLimiter LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if (!LIMITER.tryAcquire()) {
            throw new RateLimitException();
        }
        return null;
    }
}
