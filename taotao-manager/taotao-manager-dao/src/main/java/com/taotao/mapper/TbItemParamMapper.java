package com.taotao.mapper;


import com.taotao.pojo.TbItemParam;

public interface TbItemParamMapper {
    /**
     * 根据分类id查询数据库中规格参数模板表
     * @param cId 分类id
     * @return 当前分类id所对应的模板对象
     */
    TbItemParam getItemParamByCid(long itemCatId);
}