package cn.how2j.trend.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 指数类
 * @author
 */
@Data
public class Index implements Serializable{
    String code;
    String name;
}