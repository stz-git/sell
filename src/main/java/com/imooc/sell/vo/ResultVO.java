package com.imooc.sell.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<E> implements Serializable {

    private static final long serialVersionUID = 5253866544262554141L;
    private Integer code;
    private String msg;
    private E data;
}
