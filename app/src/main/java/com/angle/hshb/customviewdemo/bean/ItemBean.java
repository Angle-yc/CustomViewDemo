package com.angle.hshb.customviewdemo.bean;

/**
 * 作者    angle
 * 时间    2020-06-02 10:23
 * 文件    CustomViewDemo
 * 描述
 */
public class ItemBean {
    String value;
    Class clazz;

    public ItemBean(){}

    public ItemBean(String value, Class clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
