package com.imooc.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Stock {
    @Id
    private Integer productId;
    private Integer stockNum;
}
