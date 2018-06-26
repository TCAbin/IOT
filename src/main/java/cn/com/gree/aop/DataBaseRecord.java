package cn.com.gree.aop;

import cn.com.gree.aop.annotation.DataBaseLogParse;
import cn.com.gree.aop.logEntity.OperateLog;
import cn.com.gree.dao.BaseDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component(value = "DataBaseRecord")
public class DataBaseRecord {
   @Resource(name = "BaseDao")
    private BaseDao baseDao;

   @Pointcut("@annotation(cn.com.gree.aop.annotation.DataBaseLog)")
    public  void log(){}

    @After("log()")
    public void AfterLog(JoinPoint jp){
        Object[] params = jp.getArgs();
        OperateLog ol = DataBaseLogParse.parse(jp.getTarget().getClass(),jp.getSignature().getName());
        if(ol != null){
            ol.setUserName("admin");
            baseDao.save(ol);
        }
    }

}
