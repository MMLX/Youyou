package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;
    private TaotaoResult result;

    @Override
    public TaotaoResult checkData(String param, int type) {
        if (type == 1) {
            TbUser tbUser = tbUserMapper.getUserByUserName(param);
            if (tbUser != null) {
                return TaotaoResult.ok(false);
            }
        } else if (type == 2) {
            TbUser tbUser = tbUserMapper.getUserByPhone(param);
            if (tbUser != null) {
                return TaotaoResult.ok(false);
            }
        } else if (type == 3) {
            TbUser tbUser = tbUserMapper.getUserByEmail(param);
            if (tbUser != null) {
                return TaotaoResult.ok(false);
            }
        } else {
            return TaotaoResult.build(500, "ok", "传入的参数类型不合法");
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult createUser(TbUser tbUser) {
        //校验账号是否为空
        if(StringUtils.isBlank(tbUser.getUserName())){
            return TaotaoResult.build(400,"用户名不能为空");
        }
        //校验密码是否为空
        if(StringUtils.isBlank(tbUser.getPassWord())){
            return  TaotaoResult.build(400,"密码不能为空");
        }
        //校验手机号是否为空
        if(StringUtils.isBlank(tbUser.getPhone())){
            return  TaotaoResult.build(400,"手机号码不能为空");
        }
        //校验email是否为空
        if(StringUtils.isBlank(tbUser.getEmail())){
            return  TaotaoResult.build(400,"邮箱不能为空");
        }
        //校验账号是否重复
        result = checkData(tbUser.getUserName(), 1);
        if(!(boolean) result.getData()){
            return TaotaoResult.build(400,"此用户名已经被使用");
        }
        //校验手机号码是否重复
        result = checkData(tbUser.getPhone(),2);
        if(!(boolean) result.getData()){
            return TaotaoResult.build(400,"此手机号码已经被使用");
        }
        //校验邮箱是否重复
        result = checkData(tbUser.getEmail(),3);
        if(!(boolean) result.getData()){
            return TaotaoResult.build(400,"此邮箱已经被使用");
        }

        // 2、补全TbUser其他属性。
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        //3、密码要md5加密
        String md5PassWord = DigestUtils.md5DigestAsHex(tbUser.getPassWord().getBytes());
        tbUser.setPassWord(md5PassWord);

        tbUserMapper.insertUser(tbUser)
        ;

        return TaotaoResult.ok();
    }
}
