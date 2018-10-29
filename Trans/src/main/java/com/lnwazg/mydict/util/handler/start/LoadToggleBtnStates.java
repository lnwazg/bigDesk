package com.lnwazg.mydict.util.handler.start;

import javax.swing.JToggleButton;

import com.lnwazg.dbkit.tools.dbcache.tablemap.DBConfigHelper;
import com.lnwazg.kit.handlerseq.IHandler;
import com.lnwazg.kit.singleton.B;
import com.lnwazg.mydict.util.WinMgr;

public class LoadToggleBtnStates implements IHandler
{
    DBConfigHelper dbConfigHelper = B.q(DBConfigHelper.class);
    
    @Override
    public void handle()
    {
        //加载开关按钮
        JToggleButton toggleViewWordBook = WinMgr.handlePanel.getToggleViewWordBook();
        if (toggleViewWordBook.isSelected() != dbConfigHelper.getAsBoolean("openWordBook"))
        {
            //若状态不同，则要主动触发一次点击事件
            toggleViewWordBook.doClick();
        }
        
        //加载开关按钮
        JToggleButton toggleAutoQuery = WinMgr.handlePanel.getToggleAutoQuery();
        if (toggleAutoQuery.isSelected() != dbConfigHelper.getAsBoolean("autoQuery"))
        {
            toggleAutoQuery.doClick();
        }
        
        //加载开关按钮
        JToggleButton toggleAutoSpeak = WinMgr.handlePanel.getToggleAutoSpeak();
        if (toggleAutoSpeak.isSelected() != dbConfigHelper.getAsBoolean("autoSpeak"))
        {
            //若状态不同，则要主动触发一次点击事件
            toggleAutoSpeak.doClick();
        }
    }
}
