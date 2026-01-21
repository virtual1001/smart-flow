package com.smart.flow.spi;

import java.util.*;

/**
 * @Author pw
 * @Date 2025/11/21 17:32
 * @Version 1.0
 **/
public class BeanSwitchSpi {
    public static BeanManageSpi  beanManageSpi;
    public static  BeanManageSpi getBeanManage(){
        if (Objects.isNull(beanManageSpi)){
            List<BeanManageSpi> list = new ArrayList<>();
            ServiceLoader<BeanManageSpi> load = ServiceLoader.load(BeanManageSpi.class);
            load.forEach(list::add);
            list.sort(Comparator.comparingInt(BeanManageSpi::sort));
            beanManageSpi = list.get(0);
        }
        return beanManageSpi;
    }

}
