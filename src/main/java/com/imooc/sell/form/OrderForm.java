package com.imooc.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "name require")
    private String name;

    @NotEmpty(message = "phone require")
    private String phone;

    @NotEmpty(message = "address require")
    private String address;

    @NotEmpty(message = "openid require")
    private String openid;

    @NotEmpty(message = "car empty")
    private String items;
}
