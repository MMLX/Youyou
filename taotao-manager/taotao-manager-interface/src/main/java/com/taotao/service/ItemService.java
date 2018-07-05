package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	/**
	 * 根据商品id查询指定商品信息
	 * @param itemId 商品id
	 * @return 指定商品id的信息
	 */
	public TbItem getItemById(long itemId);
	/**
	 * 分页显示商品信息
	 * @param page 当前页
	 * @param rows 每页显示条数 默认30条
	 * @return 返回当前页面的记录条数，注意必须是json类型
	 */
	public EasyUIDataGridResult getItemList(int page, int rows);
	/**
	 * 添加商品信息，注意有些数据页面没有传递过来需要手动指定 比如商品id
	 * @param tbitem 需要添加的商品对象信息
	 * @param desc 商品描述信息
	 * @return 一个自己定义的结果集  里面包含了 {状态码,消息,数据}
	 */
	public TaotaoResult addItem(TbItem tbitem, String desc);
	
}
