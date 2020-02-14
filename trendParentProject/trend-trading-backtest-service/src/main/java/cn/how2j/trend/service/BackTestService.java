package cn.how2j.trend.service;

import cn.how2j.trend.client.IndexDataClient;
import cn.how2j.trend.pojo.IndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BackTestService {
    @Autowired
    private IndexDataClient indexDataClient;

    /**
     * 列表 倒序
     * @param code
     * @return
     */
    public List<IndexData> listIndexData(String code){
        List<IndexData> indexDatas = indexDataClient.getIndexData(code);
        Collections.reverse(indexDatas);
        for (IndexData indexData : indexDatas){
            System.out.println(indexData.getDate());
        }
        return indexDatas;
    }
}
