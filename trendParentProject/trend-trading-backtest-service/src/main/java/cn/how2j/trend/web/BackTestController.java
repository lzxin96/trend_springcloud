package cn.how2j.trend.web;

import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.service.BackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@RestController
public class BackTestController {
    @Autowired
    private BackTestService backTestService;

    @GetMapping("/simulate/{code}")
    @CrossOrigin
    public Map<String, Object> backTest(@PathVariable("code") String code) throws Exception{
        List<IndexData> indexData = backTestService.listIndexData(code);
        Map<String, Object> map = new HashMap<>();
        map.put("indexDatas", indexData);
        return map;
    }
}
