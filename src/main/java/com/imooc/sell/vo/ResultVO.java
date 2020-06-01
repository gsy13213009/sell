package com.imooc.sell.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 6789875563893296223L;

    private Integer code;

    private String msg;

    private T data;

}
