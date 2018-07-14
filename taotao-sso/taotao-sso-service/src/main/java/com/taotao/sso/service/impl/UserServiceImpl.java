package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;

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
}
