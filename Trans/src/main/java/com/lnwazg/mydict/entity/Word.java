package com.lnwazg.mydict.entity;

import com.lnwazg.dbkit.anno.entity.AutoIncrement;
import com.lnwazg.dbkit.anno.entity.Comment;
import com.lnwazg.dbkit.anno.entity.Id;
import com.lnwazg.dbkit.anno.entity.Index;
import com.lnwazg.dbkit.template.entity.TimestampEntityTemplate;

@Comment("单词表")
public class Word extends TimestampEntityTemplate
{
    @Id
    @AutoIncrement
    @Comment("主键")
    private Integer id;
    
    @Index
    @Comment("单词名称")
    public String name;
    
    @Comment("单词释义")
    public String value;
    
    public Integer getId()
    {
        return id;
    }
    
    public Word setId(Integer id)
    {
        this.id = id;
        return this;
    }
    
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
