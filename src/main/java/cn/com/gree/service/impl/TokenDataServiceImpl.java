package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.TokenData;
import cn.com.gree.service.TokenDataService;
import cn.com.gree.utils.IOTUtils.DataCollector;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("TokenDataService")
public class TokenDataServiceImpl implements TokenDataService {


    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    @Override
    public void refreshToken() {
        String token = DataCollector.getToken();
        TokenData data = new TokenData();
        data.setDate(new Date());
        data.setToken(token);
        baseDao.save(data);
    }
}
