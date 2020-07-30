package com.keith.service.impl;

import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductInfo;
import com.keith.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductService productService;

    @Test
    public void findUpGoods() throws Exception {
        List<ProductInfo> productList = productService.findUpGoods();
        Assert.assertTrue(productList.size() >0);
    }

    @Test
    public void findList() throws Exception {
        List<ProductInfo> productInfos = productService.findList(Arrays.asList("164103465734242707"));
        Assert.assertTrue(productInfos != null);
    }

    @Test
    public void decreaseStock() throws Exception {
        CarDTO carDTO = new CarDTO("157875196366160022", 3);
        productService.decreaseStock(Arrays.asList(carDTO));
    }
}