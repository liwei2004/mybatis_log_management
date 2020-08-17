package cn.itcast.aop.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 根据AOP拦截来判断到底走的是写的数据库还是读数据库
 */
@Component
@Aspect
@EnableAspectJAutoProxy//开启自动代理
@Order(-9999)//控制次类的加载顺序，值越低，越优先加载
public class DataSourceAspect {


    @Before("execution(* cn.itcast.service.*.*(..))")
    public void beforeExecute(JoinPoint joinPoint){

        String name = joinPoint.getSignature().getName();
        System.out.println("------> 拦截的方法名 : " + name);

        for (String key : ChooseDataSource.METHOD_TYPE_MAP.keySet()) {
            for (String type : ChooseDataSource.METHOD_TYPE_MAP.get(key)) {
                if(name.startsWith(type)){
                    DataSourceHandler.putDataSource(key);
                    System.out.println("---------> 获取当前使用的数据库连接池 : " + key);
                    break;
                }
            }
        }

    }


}
