package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//query/itemcatid/分类id
@RequestMapping("/item/param")
public class ItemParamController {
    //这里报错可能是编辑器的问题
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{itemCatIid}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatIid){
        TaotaoResult result = itemParamService.getItemParamByCid(itemCatIid);
        return  result;
    }


}
