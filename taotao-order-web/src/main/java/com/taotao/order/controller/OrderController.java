package com.taotao.order.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Value("${TT_CART}")
    private String TT_CART;
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        /**
         * 1.点击订单的时候 是要登录状态才行的 所以能够进入这个方法的时候一定经过了拦截器去判断是否登录
         * 2.能够进来表示一定登录了 那么一定有用户id
         * 3.用户id应该对应一张表（地址表） 但是呢 我们没有这张表，所以直接写死
         * 4.我们要从cookie或者redis中取商品信息（购物车是否同步）
         * 5.吧取到的数据存入域里面
         * 6.跳转页面
         */
        List<TbItem> cartList = getCartList(request);
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }
    private List<TbItem> getCartList(HttpServletRequest request) {
        //取购物车列表
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        //判断json是否为null
        if (StringUtils.isNotBlank(json)) {
            //把json转换成商品列表返回
            List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
            return list;
        }
        return new ArrayList<>();
    }

}
