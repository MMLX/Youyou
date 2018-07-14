package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserService {
    /**
     * 检查数据是否可用
     * @param param 需要检查的数据
     * @param type 检查的类型 1：用户名 2：手机号码 3：邮箱
     * @return {status：200,msg:"ok",data:"true|false"}如果为true则表示数据可用
     */
    TaotaoResult checkData(String param,int type);
}
