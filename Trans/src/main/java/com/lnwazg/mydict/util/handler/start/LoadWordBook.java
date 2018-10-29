package com.lnwazg.mydict.util.handler.start;

import java.util.ArrayList;
import java.util.List;

import com.lnwazg.dbkit.tools.dbcache.tablemap.DBConfigHelper;
import com.lnwazg.kit.handlerseq.IHandler;
import com.lnwazg.kit.singleton.B;
import com.lnwazg.mydict.bean.WordBook;
import com.lnwazg.mydict.util.wordbook.WordbookHelper;

public class LoadWordBook implements IHandler
{
    DBConfigHelper dbConfigHelper = B.q(DBConfigHelper.class);
    
    @Override
    public void handle()
    {
        //加载生词本
        WordBook wordBook = dbConfigHelper.getAs("wordBook", WordBook.class);
        if (wordBook == null)
        {
            wordBook = new WordBook();
            wordBook.setWords(new ArrayList<String>());
            wordBook.setTransResults(new ArrayList<String>());
        }
        List<String> words = wordBook.getWords();
        List<String> trans = wordBook.getTransResults();
        if ((words == null && trans != null) || (words != null && trans == null) || (words == null && trans == null)
            || (words != null && trans != null && words.size() != trans.size()))
        {
            //重建整个词库
            System.out.println("生词与释义数量不一致，需要升级词库。重建中...");
            dbConfigHelper.remove("wordBook");
            System.out.println("重建词库完成...");
        }
        else
        {
            WordbookHelper.refreshPanel();
        }
    }
    
}
