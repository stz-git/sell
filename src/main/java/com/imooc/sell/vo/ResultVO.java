package com.imooc.sell.vo;

import lombok.Data;

@Data
public class ResultVO<E> {
    private Integer code;
    private String msg;
    private E data;
}
