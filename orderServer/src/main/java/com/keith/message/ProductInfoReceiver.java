package com.keith.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.keith.common.output.ProductInfoOutput;
import com.keith.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        if (StringUtils.isEmpty(message) || "[]".equals(message)) {
            return;
        }
        Object object = JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutput>>() {});
        if (object instanceof List && ((List) object).get(0) instanceof ProductInfoOutput) {
            List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutput>>() {});
            log.info("从队列【{}】接收到消息：{}", "productInfo ", productInfoOutputList);

            // 存储到redis中
            for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
                stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
                        String.valueOf(productInfoOutput.getProductStock()));
            }
        } else {
            log.info("productInfoOutput changed failed");
        }
    }

}
