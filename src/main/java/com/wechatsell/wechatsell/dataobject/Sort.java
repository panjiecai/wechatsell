package com.wechatsell.wechatsell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类型表
 */
@Entity
@Data
@DynamicUpdate //实时更新
public class Sort {

    @Id
    @GeneratedValue //自增
    private Integer sortId; //类型id

    private String sortName; //类型名字

    private Integer sortType; //类型编号

    public Sort(){
    }
    public Sort(String sortName, Integer sortType) {
        this.sortName = sortName;
        this.sortType = sortType;
    }
//    public Date createTime;//创建时间
//
//    public Date updateTime;//更改时间
}
