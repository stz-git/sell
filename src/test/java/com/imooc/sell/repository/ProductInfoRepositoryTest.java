package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("羊杂碎");
        productInfo.setProductPrice(new BigDecimal(18.0));
        productInfo.setProductStock(50);
        productInfo.setProductDescription("宁夏的羊杂碎是最好吃最正宗的！");
        productInfo.setProductIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550488852207&di=06e87d3d9e33bb392dee90f973010bf2&imgtype=0&src=http%3A%2F%2Fd.ifengimg.com%2Fw600%2Fp0.ifengimg.com%2Fpmop%2F2018%2F1107%2F127838A675B8B8C8F18B5ED5B1ED6C9A734D562A_size108_w1080_h810.jpeg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);

        repository.save(productInfo);
    }

    @Test
    public void findByProductStatusTest(){
        List<ProductInfo> list = repository.findByProductStatus(0);
        System.out.println(list);
    }
}