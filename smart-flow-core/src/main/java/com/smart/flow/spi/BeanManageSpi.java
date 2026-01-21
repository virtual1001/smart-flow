package com.smart.flow.spi;

/**
 * @Author pw
 * @Date 2025/11/21 15:51
 * @Version 1.0
 **/
public interface BeanManageSpi extends SpiSort {

    <T> T getBean(String name);

    <T> T getBean(Class<T> clazz);

    <T> T registerBean(String beanName, Class<T> clazz);

    <T> T registerBean(Class<T> clazz);
}
