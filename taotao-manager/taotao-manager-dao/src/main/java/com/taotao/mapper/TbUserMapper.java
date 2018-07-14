package com.taotao.mapper;


import com.taotao.pojo.TbUser;

public interface TbUserMapper {
    /**
     * 根据用户名查询数据库
     *
     * @param userName 用户名
     * @return TbUser如果对象不为null则表示用户已经存在
     */
    TbUser getUserByUserName(String userName);

    /**
     * 根据手机号码查询数据库
     * @param phone 手机号
     * @return TbUser如果对象不为null则表示手机号码已经存在
     */
    TbUser getUserByPhone(String phone);

    /**
     * 根据emai查询数据库
     * @param email email
     * @return TbUser如果对象不为null则表示email已经存在
     */
    TbUser getUserByEmail(String email);
}