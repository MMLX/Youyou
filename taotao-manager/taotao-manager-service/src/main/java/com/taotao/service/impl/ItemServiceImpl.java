package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Service替代 bean标签
 * id itemServiceImpl
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ActiveMQTopic topic;

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired 
	private TbItemDescMapper tbitemdescMapper;
	@Override
	public TbItem getItemById(long itemId) {
		TbItem tbitem = tbItemMapper.getItemById(itemId);
		return tbitem;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//使用分页插件
		PageHelper.startPage(page, rows);
		
		//调用mapper查询数据库
		List<TbItem> itemList = tbItemMapper.getItemList();
		//通过商品的代码查询得到所有的商品信息 吧他作为参数丢入到 mybatis的分页插件对象里面 自动分页
		PageInfo<TbItem> info = new PageInfo<TbItem>(itemList);
		//
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		//从分页插件里面得到结果集
		result.setTotal(info.getTotal());
		//得到结果集
		result.setRows(itemList);

		return result;
	}
	@Override
	public TaotaoResult addItem(TbItem tbitem,String desc) {
		/**
		 * 页面传递过来的数据  cid 分类id  title商品标题  sellPoint商品买点 
		 * 				price商品价格 num库存 barcode条形码 uploadFile图片上传 desc富文本编辑器
		 * Tbitem类上面的属性  对于页面上面 没有的是 id status created updated image
		 */
		final long itemId = IDUtils.genItemId();
		tbitem.setId(itemId);
		//默认正常状态
		tbitem.setStatus((byte) 1);
		Date date = new Date();
		tbitem.setCreated(date);
		tbitem.setUpdated(date);
		tbItemMapper.insertTbItem(tbitem);
		//注意  上面的代码 可以保证 商品表的数据全部添加完毕 ，但是呢，添加商品的时候
		/**
		 * 由于是商城系统， 本来默认的情况下 商品信息和商品描述应该在一张表下面
		 * 但是如果他们在一张表里面 我们查询的时候 除了查询商品信息还要查询商品描述
		 * 而商品描述 
		 * 分表  商品表（商品信息本身+商品描述信息） ==>因为商品描述信息太大了 所以呢 我吧一张表拆分成为两张表 这样在查询 或者在插入的时候 速度会变快
		 * 在下面必须加上 添加商品分类的dao操作
		 */
		TbItemDesc tbitemdesc = new TbItemDesc();
		//设置desc描述表中的id
		tbitemdesc.setItemId(itemId);
		//添加商品描述
		tbitemdesc.setItemDesc(desc);
		tbitemdesc.setCreated(date);
		tbitemdesc.setUpdated(date);
		//吧商品描述信息加入到 描述表中
		tbitemdescMapper.insertTbitemdesc(tbitemdesc);

		/**
		 * 	在这里发布消息 更新缓存
		 * 	1.用点对点还是 订阅与发布(因为以后 有可能要同步其他地方，比如订单，购物车。。。)
		 * 	2.发送过去的数据是什么类型的数据 格式是什么？
		 * 		只发送id（）
		 * 		发送json
		 */
		jmsTemplate.send(topic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送id
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});



		return TaotaoResult.build(200, "保存商品成功");
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		TbItemDesc itemDesc = tbitemdescMapper.getItemDescById(itemId);
		return itemDesc;
	}


}
