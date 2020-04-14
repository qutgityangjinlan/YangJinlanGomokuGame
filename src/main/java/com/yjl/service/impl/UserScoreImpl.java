package com.yjl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjl.dao.UserScoreDao;
import com.yjl.pojo.Score;
import com.yjl.pojo.UserLog;
import com.yjl.service.UserScoreService;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "userScoreService")
public class UserScoreImpl implements UserScoreService {
    @Autowired
    UserScoreDao userScoreDao;
    @Override
    public Score selectByUserId(String userid) {
        return userScoreDao.selectById(userid);
    }

    @Override
    public int updateByUserId(Score score) {
        return userScoreDao.updateById(score);
    }

    @Override
    public int insertScore(Score score) {
        return userScoreDao.insert(score);
    }
}
