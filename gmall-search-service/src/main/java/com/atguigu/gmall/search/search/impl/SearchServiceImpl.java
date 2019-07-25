package com.atguigu.gmall.search.search.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsSearchParam;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;
import com.atguigu.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/15 | 12:53
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam){

        String dslStr = getSearchDsl(pmsSearchParam);
        System.err.println(dslStr);

        /*用api执行复杂查询*/
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        Search search = new Search.Builder(dslStr).addIndex("gmall0105").addType("PmsSkuInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
            /*替换高亮关键字*/
            Map<String, List<String>> highlight = hit.highlight;
            /*当不用关键字搜索时，Map<String, List<String>> highlight = hit.highlight;
            * 得到空的highlight，为了防止highlight.get("skuName").get(0);出现空指针异常
            * */
            if (highlight!=null){
                String skuName = highlight.get("skuName").get(0);
                pmsSearchSkuInfo.setSkuName(skuName);
            }
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        System.out.println(pmsSearchSkuInfos.size());
        return pmsSearchSkuInfos;
    }

    private String getSearchDsl(PmsSearchParam pmsSearchParam) {

        String[] valueId = pmsSearchParam.getValueId();
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();

        /*jest的dsl工具*/
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        /*bool*/
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        /*filter   , 在filter里面可以加term   , 相当于"pmsSkuAttrValue.valueId":"2" , 多个term可以new一个TermsQueryBuilder*/
        if (!StringUtils.isEmpty(catalog3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id" , catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (valueId!=null){
            for (String skuAttrValue : valueId) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("pmsSkuAttrValue.valueId" , skuAttrValue);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        /*must   ,  在must里面可以加match  , 相当于"skuName": "P30Pro"*/
        if (!StringUtils.isEmpty(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName" , keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        /*query*/
        searchSourceBuilder.query(boolQueryBuilder);
        /*from*/
        searchSourceBuilder.from(0);
        /*size*/
        searchSourceBuilder.size(20);
        /*highlight ，关键字高亮显示*/
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlight(highlightBuilder);
        /*sort排序 , 按照id升序 ， 降序 ，这里存在String与long类型转换问题，待解决*/
        searchSourceBuilder.sort("id" , SortOrder.DESC);

        /*最后转化成String返回给前台*/
        return searchSourceBuilder.toString();
    }
}
