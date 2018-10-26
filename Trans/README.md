# Trans 让翻译进化

- TODO:搞定Dbkit支持sqlite顺序存储(通过设置项支持sqlite顺序存储开关)
- TODO：支持byte[]数据存取
- TODO:所有资源数据库化处理
- TODO:将图片和声音文件压缩后存到数据库文件中，以提高磁盘利用率，并提高便携性（2018-10-25）
- 可一键调整查询窗体大小
- TODO：Swing paint方法深究，以优化UI体验效果
//TODO 图片服务定制  StartLocalHttpServer
//TODO 图片存储到DB中
//TODO 图片类型不需要了！
//DB顺序存储
//重构迁移
GSON配置迁移到合适的目录
public class WordBook
{
    private List<String> words;
    private List<String> transResults;
    /**
     * 词频
     */
    private Map<String, Integer> wordFreq;

    