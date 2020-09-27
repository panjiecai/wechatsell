package com.wechatsell.wechatsell.exception;

import com.wechatsell.wechatsell.enums.ResultEnum;

//商品异常
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMsg());//传到父类方法 即存入异常信息

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
