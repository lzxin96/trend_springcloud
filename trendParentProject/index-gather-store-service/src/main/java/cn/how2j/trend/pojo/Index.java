package cn.how2j.trend.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 指数类
 * 用于指数里的名称和代码
 * @Author xin
 */
@Data
public class Index implements Serializable {
    String code;
    String name;
}
