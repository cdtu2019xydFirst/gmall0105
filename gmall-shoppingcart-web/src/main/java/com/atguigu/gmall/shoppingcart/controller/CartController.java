package com.atguigu.gmall.shoppingcart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 熊亚东
 * @description :@LoginRequired(loginSuccess = false),用户不需要登录也能访问
 * //@LoginRequired(loginSuccess = true)用户必须登录才能继续访问
 * @date : 2019/7/16 | 21:42
 **/
@Controller
@CrossOrigin
public class CartController {

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;
    /**
     * 演示拦截器
     * 1.用户发送请求http://localhost:8084/toTrade
     * 2.拦截器拦截该请求，判断，比对用户是否携带有效token
     * 3.如果没有携带token，重定向 StringBuffer requestURL = request.getRequestURL();
     * http://localhost:8085/index?ReturnUrl="+requestURL（携带当前地址）到登录页面，用户输入id ，password ，以及验证码，点击登录按钮，提交from
     * 4.发送ajax请求http://localhost:8085/login?（from表单参数），login方法会去数据库中验证是否有id ， password，如果有，返回一个用户自定义的token数据。
     * 5.之后携带用户信息的token被再次发送http://localhost:8084/toTrade?token=(用户自定义token)
     * （http://localhost:8084/toTrade）就是ReturnUrl ， 即StringBuffer requestURL = request.getRequestURL();
     * 6.请求再次被拦截，此时用户已经有token，
     * 7.拦截器调用认证中心，辨别次token真假，如果返回假，http://localhost:8085/index?ReturnUrl="+requestURL
     * 重新登录验证，如果返回真，验证通过，覆盖oldtoken，去到toTrade（订单结算页面）
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("cartList")/*购物车订单页面*/
    @LoginRequired(loginSuccess = false)
    public String cartList(HttpServletRequest request , HttpServletResponse response ,ModelMap modelMap){
        /*判断用户是否登录*/
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String memberId = "";
        memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        if (StringUtils.isNotBlank(memberId)){
            /*已经登录，查询db ，调用cartList方法*/
            /*将用户没有登录时，加入到购物车的数据其实是临时存放在Cookie中，所以我们需要同步购物车*/
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            System.out.println(cartListCookie);
            /*=======================================================================================================================================*/
                  /*同步购物车以后一定要把在Cookie中同步的数据删除，不然每次到这里都会从Cookie同步数据*/
            /*=======================================================================================================================================*/
            CookieUtil.deleteCookie(request , response , "cartListCookie");
            if (StringUtils.isNotBlank(cartListCookie)) {
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                for (OmsCartItem omsCartItem : omsCartItems) {
                    OmsCartItem omsCartItemFromDb = cartService.ifCartsExistByUser(memberId, omsCartItem.getProductSkuId());
                    if (omsCartItemFromDb == null) {
                        /*该用户没有添加过当前商品*/
                        omsCartItem.setMemberId(memberId);
                        cartService.addCart(omsCartItem);
                    }
                }
                /*同步缓存*/
                cartService.flushCartCatch(memberId);
            }
            omsCartItems = cartService.cartList(memberId);

        }else{
            /*没有登录，查询cookie*/
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)){
                omsCartItems = JSON.parseArray(cartListCookie , OmsCartItem.class);
            }
        }

        modelMap.put("cartList" , omsCartItems);
        modelMap.put("nickname" , nickname);
        /*购物车被勾选的商品总价totalAmount*/
        BigDecimal totalAmount = getTotalAmount(omsCartItems);
        modelMap.put("totalAmount" , totalAmount);
        return "cart";
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal quantity = omsCartItem.getQuantity();
            String price = omsCartItem.getPrice();
            Long aLong = Long.valueOf(price);
            BigDecimal bigDecimal = BigDecimal.valueOf(aLong);
            totalAmount=totalAmount.add(quantity.multiply(bigDecimal));
        }
        return totalAmount;
    }

    @RequestMapping("addToCart")
    @LoginRequired(loginSuccess = false)
    public String addToCart(String skuId , BigDecimal quantity , HttpServletRequest request , HttpServletResponse response){

        List<OmsCartItem> omsCartItems = new ArrayList<>();

        /*调用商品服务查询商品信息*/
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId, "");
        /*将商品信息封装成购物车信息*/
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(quantity);
        omsCartItem.setProductSn(skuInfo.getSkuDesc());
        /*判断用户是否登录*/
        /*在Authinterceptor中将token中携带的用户信息已经写入request域中了*/
        String memberId = (String) request.getAttribute("memberId");
        if (StringUtils.isBlank(memberId)){
            /*用户没有登录*/
            /*cookie里原有的购物车数据*/
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            /*判断cookie是否为空*/
            if (StringUtils.isBlank(cartListCookie)){
                omsCartItems.add(omsCartItem);
            }else {
                //cookie不为空
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                /*判断我们即将添加购物车数据在cookie中是否存在*/
                boolean exist = if_cart_exist(omsCartItems , omsCartItem);
                if(exist){
                    //之前添加过，更新购物车的添加数量,所有商品价格
                    for (OmsCartItem cartItem : omsCartItems) {
                        if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                            /*更新数量*/
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                            /*更新总计价格 , String-->int , Integer.valueOf(omsCartItem.getPrice())*/
                            //cartItem.setCartAllPrice(cartItem.getCartAllPrice()+omsCartItem.getPrice()*omsCartItem.getQuantity());
                        }
                    }
                }else {
                    //之前没有添加过 ， 新增这条购物车
                    omsCartItems.add(omsCartItem);
                }
            }

            CookieUtil.setCookie(request , response , "cartListCookie" , JSON.toJSONString(omsCartItems) , 60*60*72 , true);
        }else{
            /*用户已经登录*/
            /*根据用户id,从数据库db中查询用户购物车信息，方便后面判断是该添加，还是更新购物车*/
            OmsCartItem omsCartItemFromDb = cartService.ifCartsExistByUser(memberId , skuId);
            if (omsCartItemFromDb == null){
                /*该用户没有添加过当前商品*/
                omsCartItem.setMemberId(memberId);
                omsCartItem.setQuantity(quantity);
                cartService.addCart(omsCartItem);
            }else{
                /*该用户添加过当前商品*/
                omsCartItemFromDb.setQuantity(omsCartItemFromDb.getQuantity().add(omsCartItem.getQuantity()));
                cartService.updataCart(omsCartItemFromDb);
            }
            /*同步缓存*/
            cartService.flushCartCatch(memberId);
        }
        return "redirect:http://localhost:8084/success";
    }

    private boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {

        boolean b = false;
        for (OmsCartItem cartItem : omsCartItems) {
            String productSkuId = cartItem.getProductSkuId();
            /*如果相等，说明即将添加到购物车的数据已经存在，不能覆盖，可以增加该商品的数量*/
            if (productSkuId.equals(omsCartItem.getProductSkuId())){
                b = true;
            }
        }
        return b;
    }
    @RequestMapping("success")
    public String success(){
        return "success";
    }
}
