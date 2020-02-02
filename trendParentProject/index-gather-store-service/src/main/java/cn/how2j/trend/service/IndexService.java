package cn.how2j.trend.service;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IndexService 服务类
 * 使用工具类RestTemplate 来获取如下地址
 *
 * @author xin
 */
@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
    @Autowired
    private RestTemplate restTemplate;

    private List<Index> indexes;

    /**
     * 刷新缓存
     * SpringContextUtil.getBean 去重新获取了一次 IndexService
     * 原因：从已经存在的方法里调用 redis 相关方法，并不能触发 redis 相关操作
     * @return
     */
    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<Index> fresh(){
        indexes = fetch_indexes_from_third_part();
        IndexService indexService = SpringContextUtil.getBean(IndexService.class);
        indexService.remove();
        return indexService.store();
    }


    /**
     * 清空缓存
     */
    @CacheEvict(allEntries = true)
    public void remove(){}

    /**
     * 保存数据
     * @return
     */
    @Cacheable(key = "'all_codes'")
    public List<Index> store(){
        System.out.println(this);
        return indexes;
    }

    /**
     * 获取数据
     * @return
     */
    @Cacheable(key="'all_codes'")
    public List<Index> get(){
        // 缓存中没有数据就返回null数组
        return CollUtil.toList();
    }

    /**
     * 访问服务器获取数据
     * @return
     */
    public List<Index> fetch_indexes_from_third_part() {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return map2Index(temp);
    }

    /**
     * 解析map重组成list
     * @param temp
     * @return
     */
    private List<Index> map2Index(List<Map> temp) {
        List<Index> indexes = new ArrayList<>();
        for (Map map : temp) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index = new Index();
            index.setCode(code);
            index.setName(name);
            indexes.add(index);
        }
        return indexes;
    }

    /**
     * 第三方服务丢失时调用
     * @return
     */
    public List<Index> third_part_not_connected(){
        System.out.println("third_part_not_connected()");
        Index index= new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }

}