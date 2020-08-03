package com.keith.controller;

import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductCategory;
import com.keith.common.model.ProductInfo;
import com.keith.common.model.ResultVO;
import com.keith.service.CategoryService;
import com.keith.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
*   @Description: 方法注释
*
*   @Author: SK-Keith
*   @Date: 2020/8/2
*/
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     * @return
     */
    @RequestMapping("/list")
    public ResultVO<List<ProductInfo>> list() {
        // 1. 查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpGoods();
        // 2. 获取类目type列表
        List<ProductCategory> categories = categoryService.findByCategoryType(Arrays.asList(11, 22));
        // 3. 查询类目;
        List<ProductInfo> productInfosReturn = new ArrayList<>();
        for (int i = 0; i < productInfoList.size(); i++) {
            ProductInfo productInfo = productInfoList.get(i);
            Integer type = productInfo.getCategoryType();
            for (int j = 0; j < categories.size() && categories.get(j).getCategoryType() == type; j++) {
                    ProductInfo p = new ProductInfo();
                    p.setCategoryType(productInfo.getCategoryType());
                    p.setProductName(productInfo.getProductName());
                    p.setProductDescription(productInfo.getProductDescription());
                    p.setProductPrice(productInfo.getProductPrice());
                    p.setProductStock(productInfo.getProductStock());
                    productInfosReturn.add(p);
            }
        }
        //4. 构造数据
        ResultVO<List<ProductInfo>> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        resultVO.setData(productInfosReturn);
        resultVO.setMsg("SUCCESS");
        return resultVO;
    }

    /**
    *   @Description: 根据产品id获取产品列表
    *   @Param: [productIdList] 产品id列表
    *   @return: java.util.List<com.keith.common.model.ProductInfo>
    *   @Date: 2020/8/2
    */
    @PostMapping("/listForOrder")
    public List<ProductInfo> getForOrder(@RequestBody List<String> productIdList) {
        return productService.findList(productIdList);
    }

    /**
    *   @Description:  根据购物车列表减库存
    *   @Param: [carDTOS]
    *   @return: void
    *   @Date: 2020/8/2
    */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CarDTO> carDTOS) {
            productService.decreaseStock(carDTOS);
    }
}
