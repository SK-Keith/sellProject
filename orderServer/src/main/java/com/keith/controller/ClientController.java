package com.keith.controller;

import com.keith.client.ProductClient;
import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
//import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientController {

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/product/getProductMsg")
    public String getProductMsg(){
        //1
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:8090/msg/getMsg", String.class);


        //2
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/msg/getMsg";
//        String response = restTemplate.getForObject(url, String.class);

        //3
//        String response = restTemplate.getForObject("http://PRODUCT/msg/getMsg", String.class);
        String response = productClient.productMsg();
        log.info("response = {}", response);
        return response;
    }

    @GetMapping("/listForOrder")
    public String getProductList(){
        List<ProductInfo> productInfos = productClient.findByProductId(Arrays.asList("164103465734242707"));
        log.info("response = {}", productInfos);
        return "ok";
    }

    @GetMapping("/productDesreaseStock")
    public String productDesreaseStock() {
        productClient.decreaseStock(Arrays.asList(new CarDTO("164103465734242707", 3)));
        return "ok";
    }
}
