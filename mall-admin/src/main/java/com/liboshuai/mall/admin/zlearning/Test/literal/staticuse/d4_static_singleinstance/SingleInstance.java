package com.liboshuai.mall.admin.zlearning.Test.literal.staticuse.d4_static_singleinstance;

/**
 * @author:Sun
 * @date04/12/20221:20 PM
 */

/**
 *饿汗单列
 */

public class SingleInstance {

    public static SingleInstance instance = new SingleInstance();
    /**
     * 必须把构造器私有化
     */
    private SingleInstance(){

    }
}
