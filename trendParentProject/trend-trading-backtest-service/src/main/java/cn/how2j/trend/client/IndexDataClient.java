package cn.how2j.trend.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cn.how2j.trend.pojo.IndexData;

/**
 * 动态代理
 * 使用 feign 模式从 INDEX-DATA-SERVICE 微服务获取数据。
 * 访问不了的时候，就去找 IndexDataClientFeignHystrix 要数据了。
 * @author
 */
@FeignClient(value = "INDEX-DATA-SERVICE", fallback = IndexDataClientFeignHystrix.class)
public interface IndexDataClient {
    @GetMapping("/data/{code}")
    List<IndexData> getIndexData(@PathVariable("code") String code);
}
