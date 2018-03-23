package com.aboruo.JMS.util;

import com.google.common.primitives.Primitives;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 类名称: SpringContextUtil
 * 类描述: spring容器工具类
 *
 * @author aboruo
 * @date 2018-03-23 10:22
 */
@Component(value = "springContextUtil")
public class SpringContextUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory = null;
    private static SpringContextUtil springContext = null;

    /**
     * 方法描述:单例方法
     * @return SpringContextUtil 实例
     * @author aboruo
     * @date 2015-10-20 14:07
     */
    public static SpringContextUtil getInstance(){
        if(springContext == null){
            springContext = new SpringContextUtil();
        }
        return springContext;
    }
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // TODO Auto-generated method stub
        SpringContextUtil.beanFactory = beanFactory;
    }
    /**
     * @Title:getBean
     * @Description: 根据bean名称获取相应的bean
     * @author:aboruo
     * @param key
     * @return Object
     * @date 2017年10月18日 下午4:45:46
     */
    public Object getBean(String key){
        if(! beanFactory.containsBean(key)) return null;
        return beanFactory.getBean(key);
    }
    /**
     * @Title:getBean
     * @Description: 根据bean名称获取相应的bean
     * @author:aboruo
     * @param key
     * @param classOfT
     * @return T
     * @date 2017年10月18日 下午5:06:51
     */
    public <T> T getBean(String key,Class<T> classOfT){
        if(! beanFactory.containsBean(key)) return null;
        Object bean = beanFactory.getBean(key);
        return Primitives.wrap(classOfT).cast(bean);
    }
}
