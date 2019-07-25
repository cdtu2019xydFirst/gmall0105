package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsSearchParam;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/15 | 12:44
 **/
public interface SearchService {

    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) throws IOException;

}
