package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;

@Controller
public class IndexController {
	//对于用户来说 他的请求地址是 http://localhost:8082/===>  http://localhost:8082/index.jsp
	//web.xml里面的拦截规则 *.html
	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;

	@Autowired
	private ContentService contentService;
	
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//首页需要什么东西 是首页必须要告诉我们的
		List<TbContent> contentList = contentService.getContentList(AD1_CID);
		List<Ad1Node> reuslt = new ArrayList<Ad1Node>(); 
		for (TbContent content : contentList) {
			Ad1Node node = new Ad1Node();
			//大图
			node.setSrcB(content.getPic2());
			//设置小图高度
			node.setHeight(AD1_HEIGHT);
			//设置提示 也就是图片的描述
			node.setAlt(content.getTitleDesc());
			node.setWidth(AD1_WIDTH);
			//小图
			node.setSrc(content.getPic());
			//设置大宽度
			node.setWidthB(AD1_WIDTH_B);
			//设置图片跳转的链接
			node.setHref(content.getUrl());
			//设置大高度
			node.setHeightB(AD1_HEIGHT_B);
			reuslt.add(node);
		}
		//吧对象转成json
		//通过 jackson来转 阿里巴巴   java对象转json数据 
		model.addAttribute("ad1", JsonUtils.objectToJson(reuslt));
		
		
		return "index";
	} 
}
