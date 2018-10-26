package com.lnwazg.mydict.bean;

import com.lnwazg.dbkit.tools.db.collection.DbHashMap;
import com.lnwazg.kit.converter.VC;
import com.lnwazg.kit.singleton.B;

/**
 * 系统配置信息
 * @author nan.li
 * @version 2014-11-17
 */
public class SystemConfig
{
    public static boolean isOpenWordbook()
    {
        return VC.of(B.q(DbHashMap.class).get("openWordbook")).getAsBoolean();
    }
    
    @SuppressWarnings("unchecked")
    public static void setOpenWordbook(boolean openWordbook)
    {
        ((DbHashMap<String, String>)B.q(DbHashMap.class)).put("openWordbook", openWordbook + "");
    }
    
    public static boolean isAutoSpeak()
    {
        return VC.of(B.q(DbHashMap.class).get("autoSpeak")).getAsBoolean();
    }
    
    @SuppressWarnings("unchecked")
    public static void setAutoSpeak(boolean autoSpeak)
    {
        ((DbHashMap<String, String>)B.q(DbHashMap.class)).put("autoSpeak", autoSpeak + "");
    }
    
    public static boolean isAutoQuery()
    {
        return VC.of(B.q(DbHashMap.class).get("autoQuery")).getAsBoolean();
    }
    
    @SuppressWarnings("unchecked")
    public static void setAutoQuery(boolean autoQuery)
    {
        ((DbHashMap<String, String>)B.q(DbHashMap.class)).put("autoQuery", autoQuery + "");
    }
    
}
