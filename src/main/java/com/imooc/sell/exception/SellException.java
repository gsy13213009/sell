package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(int i, String defaultMessage) {
        super(defaultMessage);
        this.code = i;
    }
}
