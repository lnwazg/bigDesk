package com.lnwazg.mydict.entity;

import com.lnwazg.dbkit.anno.entity.AutoIncrement;
import com.lnwazg.dbkit.anno.entity.Comment;
import com.lnwazg.dbkit.anno.entity.Id;
import com.lnwazg.dbkit.anno.entity.Index;
import com.lnwazg.dbkit.tools.entity.template.TimestampEntityTemplate;

@Comment("生词本")
public class WordBook extends TimestampEntityTemplate
{
    @Id
    @AutoIncrement
    @Comment("主键")
    Integer id;
    
    @Index
    @Comment("单词名称")
    public String word;
    
    @Comment("单词释义")
    public String trans;
    
    @Comment("单词出现的频率")
    public Integer freq;
    
    public Integer getId()
    {
        return id;
    }
    
    public WordBook setId(Integer id)
    {
        this.id = id;
        return this;
    }
    
    public String getWord()
    {
        return word;
    }
    
    public WordBook setWord(String word)
    {
        this.word = word;
        return this;
    }
    
    public String getTrans()
    {
        return trans;
    }
    
    public WordBook setTrans(String trans)
    {
        this.trans = trans;
        return this;
    }
    
    public Integer getFreq()
    {
        return freq;
    }
    
    public WordBook setFreq(Integer freq)
    {
        this.freq = freq;
        return this;
    }
}
