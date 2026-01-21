package com.smart.flow;


import com.smart.flow.spi.BeanManageSpi;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * @Author pw
 * @Date 2025/11/21 15:56
 * @Version 1.0
 **/
public class SpringBeanManageSpi implements ApplicationContextAware, BeanManageSpi {

    private static ApplicationContext applicationContext;

    public SpringBeanManageSpi() {}

    @Override
    public <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    @Override
    public <T> T registerBean(String beanName, Class<T> clazz) {
        if (StringUtils.isEmpty(beanName)) {
            beanName = clazz.getName();
        }
        if  (applicationContext.containsBean(beanName)) {
            return (T) applicationContext.getBean(beanName);
        }

        DefaultListableBeanFactory  beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setBeanClassName(clazz.getName());
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        return (T) applicationContext.getBean(beanName);
    }

    @Override
    public <T> T registerBean(Class<T> clazz) {
        return registerBean(clazz.getSimpleName(), clazz);
    }

    @Override
    public int sort() {
        return 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =  applicationContext;
    }
}
