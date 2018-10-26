package com.lnwazg.mydict;

import com.lnwazg.mydict.util.Bootstrap;

/**
 * 主程序
 * @author nan.li
 * @version 2018年10月26日
 */
public class GTmain
{
    public static void main(String[] args)
    {
        Bootstrap.prepareEnv();
        Bootstrap.initLog();
        Bootstrap.initDB();
        Bootstrap.initUtils();
        Bootstrap.initUI();
    }
}