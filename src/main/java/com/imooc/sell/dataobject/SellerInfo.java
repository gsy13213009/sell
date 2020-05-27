package com.imooc.sell.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SellerInfo {

    @Id
    @Column(name = "id")
    private String sellerId;
    private String username;
    private String password;
    private String openid;
}
