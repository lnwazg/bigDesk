package com.lnwazg.mydict.util.localdic;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.lnwazg.dbkit.proxy.sqlite.SQLiteSyncWriteAccessDao;
import com.lnwazg.kit.singleton.B;
import com.lnwazg.mydict.entity.Word;

/**
 * 字典表<br>
 * 完全有必要通过db的方式进行操作了！因为用hashmap的方式，后期会占用巨量的内存，这是完全无法接受的事情！
 * 
 * @author nan.li
 * @version 2016年4月13日
 */
public class DictMap
{
    static SQLiteSyncWriteAccessDao sqLiteSyncWriteAccessDao = B.q(SQLiteSyncWriteAccessDao.class);
    
    public static String get(String key)
    {
        try
        {
            return sqLiteSyncWriteAccessDao.findValue("select value from Word where name=?", key);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void put(String key, String value)
    {
        try
        {
            Word word = new Word().setName(key).setValue(value);
            if (StringUtils.isNotEmpty(get(key)))
            {
                sqLiteSyncWriteAccessDao.update("update Word set value=? where name=?", value, key);
            }
            else
            {
                sqLiteSyncWriteAccessDao.insert(word);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void remove(String key)
    {
        try
        {
            sqLiteSyncWriteAccessDao.execute("delete from Word where name=?", key);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
