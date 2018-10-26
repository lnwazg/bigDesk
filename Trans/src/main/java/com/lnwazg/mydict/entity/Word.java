package com.lnwazg.mydict.entity;

import com.lnwazg.dbkit.anno.entity.Comment;
import com.lnwazg.dbkit.anno.entity.Index;
import com.lnwazg.dbkit.tools.entity.template.TimestampEntityTemplate;

@Comment("单词表")
public class Word extends TimestampEntityTemplate
{
    @Index
    @Comment("单词名称")
    public String name;
    
    @Comment("单词释义")
    public String value;
    
    public String getName()
    {
        return name;
    }
    
    public Word setName(String name)
    {
        this.name = name;
        return this;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public Word setValue(String value)
    {
        this.value = value;
        return this;
    }
}
