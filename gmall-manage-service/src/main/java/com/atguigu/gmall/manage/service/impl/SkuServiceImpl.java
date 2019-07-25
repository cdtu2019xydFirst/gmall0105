package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<PmsSkuInfo> skuList(String catalog3Id) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setCatalog3Id(catalog3Id);
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.select(pmsSkuInfo);
        return pmsSkuInfos;
    }

    @Override
    public void SavePmsSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //模拟前台提交的form 表单信息，封装在PmsSkuInfo中
        PmsSkuInfo pmsSkuInfo1 = new PmsSkuInfo();
        List<PmsSkuAttrValue> pmsSkuAttrValues = new ArrayList<>();
        PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = new ArrayList<>();
        PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
        pmsSkuInfo1.setProductId("4");
        pmsSkuInfo1.setPrice("12999");
        pmsSkuInfo1.setSkuName("华为荣耀20pro手机|8+512G 全网通 MOSCHINO联名版");
        pmsSkuInfo1.setSkuDesc("4800万全焦段AI四摄|麒麟980芯片|屏内指纹");
        pmsSkuInfo1.setWeight("0.53");
        pmsSkuInfo1.setCatalog3Id("1");

        pmsSkuAttrValue.setValueId("1");//处理器
        pmsSkuAttrValue.setAttrId("1");//麒麟980
        pmsSkuAttrValues.add(pmsSkuAttrValue);
        pmsSkuInfo1.setPmsSkuAttrValue(pmsSkuAttrValues);

        pmsSkuSaleAttrValue.setSaleAttrId("6");
        pmsSkuSaleAttrValue.setSaleAttrValueId("23");
        pmsSkuSaleAttrValue.setSaleAttrName("增值业务");
        pmsSkuSaleAttrValue.setSaleAttrValueName("高价回收|卖了赚钱");
        pmsSkuSaleAttrValues.add(pmsSkuSaleAttrValue);
        pmsSkuInfo1.setPmsSkuSaleAttrValue(pmsSkuSaleAttrValues);

        //插入skuinfo
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo1);
        String skuId = pmsSkuInfo1.getId();
        //插入平台属性关联
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo1.getPmsSkuAttrValue();
        for (PmsSkuAttrValue pmsSkuAttrValue1 : pmsSkuAttrValueList) {
            pmsSkuAttrValue1.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue1);
        }
        //插入销售属性关联
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo1.getPmsSkuSaleAttrValue();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue1 : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue1.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue1);
        }
        //插入图片，暂时还没有实现，不是技术问题，是资料欠缺！
        System.out.println("以后");
    }


    /**
     * @author : 熊亚东
     * @description :使用缓存redis解决高并发问题
     * 1.缓存使用简单设计
     * 2.redis整合（redis+Spring）
     * 3.设计一个数据存储策略(如何设计key(核心))
     * *企业中的存储策略
     * *数据对象名：数对象id:对象属性（User:123:password）(User:123:username)
     * @date : 2019/7/6 | 18:29
     **/
    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
        PmsSkuInfo pmsSkuInfo1 = new PmsSkuInfo();
        pmsSkuInfo1.setId(skuId);
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo1);
        return pmsSkuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId, String ip) {
        System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "进入了商品详情的请求");
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        /*1.连接缓存*/
        Jedis jedis = redisUtil.getJedis();
        /*2.查询缓存*/
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);
        /*StringUtils.isEmpty(skuJson)*/
        if (skuJson != null && !skuJson.equals("")) {
            /*把json转化为java对象类  , 但是skuJson不能为空*/
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
            System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "从redis缓存中获取商品详情");
        } else {
            /*3.如果缓存中没有，查询mysql*/
            System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "发现缓存中没有申请缓存分布式锁：" + "sku:" + skuId + ":lock");
            /**
             * 缓存穿透问题：（1）.基于redis的nx分布式锁
             */
            /*设置分布式锁 返回值是OK*/
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10 * 1000);
            if (OK != null && !OK.equals("") && OK.equals("OK")) {
                /*设置成功，有权利在10秒的过期时间内访问数据库*/
                System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "成功拿到分布式锁，有权利在10秒的过期时间内访问数据库" + "sku:" + skuId + ":lock");
                pmsSkuInfo = getSkuByIdFromDb(skuId);

                /*测试代码：*/
                /*try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                if (pmsSkuInfo != null) {
                    /*4.mysql将想要查询的结果返回给redis*/
                    System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "mysql将数据结果返回给redis缓存中");
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));
                } else {
                    /*数据库中不存在该sku*/
                    /*为了防止缓存穿透(大量请求访问数据库mysql)，将null值或者空串给redis , 并且给null值或者空串设置3分钟的自动销毁*/
                    jedis.setex("sku:" + skuId + ":info", 60 * 3, JSON.toJSONString(""));
                }
                /*在访问MySQL后，将手中的mysql锁释放掉*/
                /*问题一：为了避免当拿到锁的用户在过期时间后依旧没有从数据库中获取到数据，锁已经自动销毁（过期时间已满）
                  这时下一个用户拿到锁，开始访问数据库，但是，上一个拿到锁的用户访问完毕，会回来执行“删除锁”
                  jedis.del("sku:" + skuId + ":lock");命令，这时删除的锁是当前正在访问的用户的锁，造成当前用户访问失败。
                  所以在当前用户拿到锁情况下，上一个用户回来做“删除锁”jedis.del("sku:" + skuId + ":lock");命令时
                  上一个用户应该将此时的newToken和自己拿到锁分配的随机token做比较，相同则是同一个用户，可以删除锁
                  确认删除的是自己的锁    "sku:" + skuId + ":info"=token  k , v 结构 */
                String newToken = jedis.get("sku:" + skuId + ":lock");
                if (newToken != null && !newToken.equals("") && newToken.equals(token)) {
                    /*问题二：当刚好代码执行到这里时，自己的token过期了，然后在if里面又把下一个用户的token删除了，怎么解决？
                      可以用lua脚本，在查询到key的同时删除该key，防止高并发下的意外发生！
                      将：String newToken = jedis.get("sku:" + skuId + ":info");
                         if(newToken!=null&&!newToken.equals("")&&newToken.equals(token)){
                             jedis.del("sku:" + skuId + ":lock");
                         }改为下面的代码：lua脚本，在查询到key的同时删除该key！
                       String script = "if redis.call('get' , KEYS[1]==ARGV[1] then return redis.call('del',KEYS[1]))
                                        else return o end";
                       jedis.eval(script,Collections.singletonList("lock"),Conllections.singletonList(token));
                    */
                    jedis.del("sku:" + skuId + ":lock");
                    System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "在访问MySQL后，将手中的mysql锁释放掉" + "sku:" + skuId + ":lock");
                }
            } else {
                /*设置失败 ，自旋（该线程在睡眠几秒后重新尝试访问getSkuById方法）*/
                System.out.println("ip为:" + ip + "的用户:" + Thread.currentThread().getName() + "没有拿到分布式锁 ，自旋（该线程在睡眠几秒后重新尝试访问getSkuById方法）");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*return不会产生新的线程，这才是自旋。如果不加return，则会产生新的getSkuById（）“孤儿”线程。*/
                return getSkuById(skuId, ip);
            }
        }

        jedis.close();/*最后一步一定要关闭jidis*/

        return pmsSkuInfo;
    }


    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getAllSku(String catalog3Id) {

        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {

            String skuId = pmsSkuInfo.getId();

            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);

            List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);

            pmsSkuInfo.setPmsSkuAttrValue(pmsSkuAttrValues);

        }

        return pmsSkuInfos;
    }

    @Override
    public boolean checkPrice(String productSkuId, String productPrice) {
        boolean b = false;
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(productSkuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        String price = skuInfo.getPrice();
        if (price != null && price.equals(productPrice)) {/*比较价格最好两个变量都是 BigDecimal 类型，准确，不易出错*/
            b = true;
        }
        return b;
    }
}
