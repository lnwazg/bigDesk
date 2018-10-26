package com.lnwazg.mydict.util.translate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.lnwazg.kit.http.apache.httpclient.HttpClientKit;
import com.lnwazg.kit.js.JsUtils;
import com.lnwazg.kit.security.SecurityUtils;
import com.lnwazg.mydict.util.Constant;
import com.lnwazg.mydict.util.translate.dictcn.DictCnTrans;

/**
 * 翻译工具
 * @author nan.li
 * @version 2014-11-6
 */
public class TranslateUtil
{
    private static Log logger = LogFactory.getLog(TranslateUtil.class);
    
    private static final String GOOGLE_TRANS_PREFIX = "https://translate.google.cn/translate_a/single?client=t";
    
    private static final String UTF8_ENCODING = "UTF-8";
    
    /**
     * 翻译的主要调用的方法
     * @author nan.li
     * @param text
     * @param target_lang 目标语言
     * @return
     */
    public static String translate(String text, String target_lang)
    {
        //        return translateBaidu(text, Language.AUTO.getValue(), target_lang);
        return translateGoogle(text, Language.AUTO.getValue(), target_lang);
    }
    
    /**
     * 百度翻译
     * @author nan.li
     * @param text
     * @param src_lang
     * @param target_lang
     * @return
     */
    public static String translateBaidu(String text, String src_lang, String target_lang)
    {
        String result = null;
        HttpClient httpClient = HttpClientKit.getDefaultHttpClient();
        try
        {
            HttpPost httpPost = new HttpPost("http://fanyi.baidu.com/v2transapi");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpPost.addHeader("Host", "fanyi.baidu.com");
            httpPost.addHeader("Origin", "http://fanyi.baidu.com");
            httpPost.addHeader("Referer", "http://fanyi.baidu.com/translate");
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Cookie", "BAIDUID=70DA5791665EF397B850693E2C0881E5:FG=1; PSTM=1499820478; BIDUPSID=78722458D4F982F0E5688249DF647F04; BDUSS=TRPQno3Ry1UUVZITHBKU0k3U3FNfmVSR2ZYakFIU21oTXkyc0VMMk9hV3BYNVZaSVFBQUFBJCQAAAAAAAAAAAEAAAAkDaAFbG53YXpnAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKnSbVmp0m1ZW; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; hasSeenTips=1; from_lang_often=%5B%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%5D; to_lang_often=%5B%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%5D; MCITY=-%3A; H_PS_PSSID=1440_21119_22159; BDSFRCVID=YoIsJeC62Z-eYGTAoMvecaE-weDDZ4rTH6aoF89jwkaZnFgDNxDOEG0PqU8g0Kub2VqXogKKL2OTHmoP; H_BDCLCKID_SF=tR-JoDDMJDL3qPTuKITaKDCShUFst-7W-2Q-5KL-fCjEEDJFLJbm3UkS-t_L--QeJTbi3MbdJJjoHI8GqtR2y-bQLlbyJx_La2TxoUJh5DnJhhvG-4PKjtCebPRiWTj9QgbLWMtLtD85bKt4D5A35n-Wql3KbtoQaITQQ5rJabC3oJ7VKU6qLT5XWmc-Xxbn0COuLJ5JKlKVfn5eQhCMKl0njxQy0jk82jvW3xc83J3U84neXUonDh8L3H7MJUntKJrpQxnO5hvv8KoO3M7VhpOh-p52f6KDJnAD3H; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; PSINO=3; locale=zh; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1513234220,1515148477,1515581923; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1515581923");
            
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            if (target_lang.equals(Language.ENGLISH.getValue()))
            {
                formparams.add(new BasicNameValuePair("from", "zh"));
                formparams.add(new BasicNameValuePair("to", "en"));
            }
            else
            {
                formparams.add(new BasicNameValuePair("from", "en"));
                formparams.add(new BasicNameValuePair("to", "zh"));
            }
            formparams.add(new BasicNameValuePair("query", text));
            
            formparams.add(new BasicNameValuePair("transtype", "translang"));
            formparams.add(new BasicNameValuePair("simple_means_flag", "3"));
            formparams.add(new BasicNameValuePair("sign", "77323.995138"));
            formparams.add(new BasicNameValuePair("token", "5e244df79837851c39a3e50c771edab6"));
            
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            
            HttpResponse response = httpClient.execute(httpPost);
            String resultJsonStr = EntityUtils.toString(response.getEntity());
            System.out.println(resultJsonStr);
            if (StringUtils.isEmpty(resultJsonStr))
            {
                return null;
            }
            JSONObject jsonObject = new JSONObject(resultJsonStr);
            result = jsonObject.getJSONObject("trans_result").getJSONArray("data").getJSONObject(0).getString("dst");
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }
    
    public static String translateYoudao(String text, String src_lang, String target_lang)
    {
        String result = null;
        HttpClient httpClient = HttpClientKit.getDefaultHttpClient();
        try
        {
            HttpPost httpPost = new HttpPost("http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpPost.addHeader("Cookie", "OUTFOX_SEARCH_USER_ID=2026021347@153.3.251.202; P_INFO=lnwazg@126.com|1506566457|2|mail126|11&16|jis&1506137312&mail_client#jis&null#10#0#0|189098&0||lnwazg@126.com; _ntes_nnid=4fe1e8403eb17b618a356ca1a3fbaf47,1510288854498; OUTFOX_SEARCH_USER_ID_NCOO=1452770823.134785; DICT_UGC=be3af0da19b5c5e6aa4e17bd8d90b28a|; JSESSIONID=abcxR0GDVU6sohYb_zudw; ___rl__test__cookies=1515583817489");
            
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            if (target_lang.equals(Language.ENGLISH.getValue()))
            {
                formparams.add(new BasicNameValuePair("from", "AUTO"));
                formparams.add(new BasicNameValuePair("to", "AUTO"));
            }
            else
            {
                formparams.add(new BasicNameValuePair("from", "AUTO"));
                formparams.add(new BasicNameValuePair("to", "AUTO"));
            }
            formparams.add(new BasicNameValuePair("i", text));
            formparams.add(new BasicNameValuePair("smartresult", "dict"));
            formparams.add(new BasicNameValuePair("client", "fanyideskweb"));
            
            //            var t = e.i,
            //                i = "" + ((new Date).getTime() + parseInt(10 * Math.random(), 10)),
            //                o = n.md5("fanyideskweb" + t + i + "aNPG!!u6sesA>hBAW1@(-");
            //                r && r.abort(),
            //                r = n.ajax({
            //                    type: "POST",
            //                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            //                    url: "/bbk/translate_m.do",
            //                    data: {
            //                        i: e.i,
            //                        client: "fanyideskweb",
            //                        salt: i,
            //                        sign: o,
            //                        tgt: e.tgt,
            //                        from: e.from,
            //                        to: e.to,
            //                        doctype: "json",
            //                        version: "3.0",
            //                        cache: !0
            //                    },
            String salt = "" + (System.currentTimeMillis() + (int)(10 * Math.random()));
            formparams.add(new BasicNameValuePair("salt", salt));
            formparams.add(new BasicNameValuePair("sign", SecurityUtils.md5Encode("fanyideskweb" + text + salt + "aNPG!!u6sesA>hBAW1@(-")));
            formparams.add(new BasicNameValuePair("doctype", "json"));
            formparams.add(new BasicNameValuePair("version", "2.1"));
            formparams.add(new BasicNameValuePair("keyfrom", "fanyi.web"));
            formparams.add(new BasicNameValuePair("action", "FY_BY_CLICKBUTTION"));
            formparams.add(new BasicNameValuePair("typoResult", "false"));
            
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            
            HttpResponse response = httpClient.execute(httpPost);
            String resultJsonStr = EntityUtils.toString(response.getEntity());
            System.out.println(resultJsonStr);
            if (StringUtils.isEmpty(resultJsonStr))
            {
                return null;
            }
            JSONObject jsonObject = new JSONObject(resultJsonStr);
            result = jsonObject.getJSONObject("trans_result").getJSONArray("data").getJSONObject(0).getString("dst");
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }
    
    /**
     * 谷歌翻译
     * @author nan.li
     * @param text
     * @param src_lang
     * @param target_lang
     * @return
     */
    public static String translateGoogle(String text, String src_lang, String target_lang)
    {
        String result = null;
        StringBuilder sb = new StringBuilder();
        HttpClient httpClient = HttpClientKit.getDefaultHttpClient();
        try
        {
            //英翻中
            //https://translate.google.cn/translate_a/single?client=t&sl=en&tl=zh-CN&hl=zh-CN
            //&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t
            //&ie=UTF-8&oe=UTF-8
            //&ssel=3&tsel=3&kc=0&tk=249756.369249
            //&q=good
            
            //https://translate.google.cn/translate_a/single?client=t&sl=en&tl=zh-CN&hl=zh-CN
            //&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t
            //&ie=UTF-8&oe=UTF-8
            //&source=btn&ssel=3&tsel=3&kc=0&tk=767145.907604
            //&q=monster
            
            //中翻英
            //https://translate.google.cn/translate_a/single?client=t&sl=zh-CN&tl=en&hl=zh-CN
            //&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t
            //&ie=UTF-8&oe=UTF-8
            //&source=btn
            //&ssel=6&tsel=3&kc=0&tk=504406.122795
            //&q=%E5%A5%BD%E4%BA%BA
            
            //结论： tk值是一个特殊的token值，是需要通过一个加密算法去求得的。
            //该加密算法是JavaScript写的，可以用java内置的scriptEngine去加载计算
            
            String url = GOOGLE_TRANS_PREFIX + "&sl=" + src_lang + "&tl=" + target_lang + "&hl=" + src_lang
                + "&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t"
                + "&ie=" + UTF8_ENCODING + "&oe=" + UTF8_ENCODING
                + "&ssel=" + (src_lang.equals(Language.ENGLISH.getValue()) ? "3" : "6") + "&tsel=3&kc=0&tk="
                //                + (src_lang.equals(Language.ENGLISH.getValue()) ? "681069.808723" : "504406.122795")
                + JsUtils.invokeByJsContent(IOUtils.toString(TranslateUtil.class.getClassLoader().getResourceAsStream("js/goodTk.js"), Constant.UTF8_ENCODING), "tk", text)
                + "&q=" + URLEncoder.encode(text, UTF8_ENCODING);
            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            String allInfo = EntityUtils.toString(response.getEntity());
            //            System.out.println(allInfo);
            String[] resultArray = allInfo.split("]]")[0].split("]");
            for (int i = 0; i < resultArray.length - 1; i++)
            {
                sb.append(resultArray[i].split("\"")[1]);
            }
            result = sb.toString();
            result = result.replace("\\n", "\r\n");
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }
    
    /**
     * 获取dict.cn上面的详尽解释
     * @author nan.li
     * @param text
     * @return
     */
    public static DictCnTrans translateDictCn(String text)
    {
        //1.get方法获取页面信息。从中获取音标信息以及当前页面的key、session信息。（注意：翻译的内容不一定存在）
        //2.如果翻译的内容存在的话，则拼接参数来获取json信息，然后解析该json信息的内容
        
        HttpClient httpClient = HttpClientKit.getDefaultHttpClient();
        //处理请求，得到响应  
        try
        {
            HttpGet httpGet = new HttpGet(String.format("http://apii.dict.cn/mini.php?q=%s", text));
            HttpResponse response = httpClient.execute(httpGet);
            String pageReturn = EntityUtils.toString(response.getEntity(), "gb2312");
            if (StringUtils.isEmpty(pageReturn))
            {
                return null;
            }
            //音标信息
            String yinbiao = StringUtils.substringBetween(pageReturn, "<span class='p'> ", "</span>");
            String fanyi = StringUtils.substringBetween(pageReturn, "<div id=\"e\">", "</div>");//翻译
            if (StringUtils.isEmpty(fanyi))
            {
                //连翻译都没有，则的确什么也没有！
                return null;
            }
            String liju = StringUtils.substringBetween(pageReturn, "<div id=\"s\">", "</div>");//例句
            String bianhua = StringUtils.substringBetween(pageReturn, "<div id=\"t\">", "</div>");//变化
            String audioUrl = getAudioUrl(text);
            DictCnTrans dictCnTrans = new DictCnTrans();
            dictCnTrans.setSrc(text);
            dictCnTrans.setYinbiao(yinbiao);
            dictCnTrans.setAudioUrl(audioUrl);
            //对翻译中的尖括号作特殊处理
            fanyi = handleSpecialJiankuohao(fanyi);
            dictCnTrans.setFanyi(fanyi);
            if (StringUtils.isNotEmpty(liju))
            {
                liju = removeAllBtwn(liju, "<a", "</a>");
                dictCnTrans.setLiju(liju);
            }
            if (StringUtils.isNotEmpty(bianhua))
            {
                dictCnTrans.setBianhua(bianhua);
            }
            dictCnTrans.setNetFail(false);
            return dictCnTrans;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DictCnTrans dictCnTrans = new DictCnTrans();
            dictCnTrans.setNetFail(true);//标记了网络请求失败了这一情况
            return dictCnTrans;
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }
    
    /**
     * 获取发音文件地址数据
     * @author nan.li
     * @param text
     * @return
     */
    private static String getAudioUrl(String text)
    {
        String result = null;
        String url = String.format("http://dict.cn/%s", text);
        try
        {
            org.jsoup.nodes.Document doc = Jsoup.connect(url)
                .header("User-Agent",
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4")
                .get();
            Elements element = doc.select("#content > div.main > div.word > div.phonetic > span:nth-child(2) > i.sound.fsound");
            if (element.isEmpty())
            {
                //do nothing
            }
            else
            {
                result = element.first().attr("naudio");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * hai ci web api更新之后，该方法已失效，最新方法请参考：translateDictCn()
     * @author nan.li
     * @param text
     * @return
     */
    @Deprecated
    public static DictCnTrans translateDictCnOld(String text)
    {
        //1.get方法获取页面信息。从中获取音标信息以及当前页面的key、session信息。（注意：翻译的内容不一定存在）
        //2.如果翻译的内容存在的话，则拼接参数来获取json信息，然后解析该json信息的内容
        
        HttpClient httpClient = HttpClientKit.getDefaultHttpClient();
        //处理请求，得到响应  
        try
        {
            HttpGet httpGet = new HttpGet(String.format("http://apii.dict.cn/mini.php?q=%s", text));
            HttpResponse response = httpClient.execute(httpGet);
            String pageReturn = EntityUtils.toString(response.getEntity(), "gb2312");
            if (StringUtils.isEmpty(pageReturn))
            {
                return null;
            }
            String pageToken = StringUtils.substringBetween(pageReturn, "dict_pagetoken=\"", "\"");//key
            String yinbiao = StringUtils.substringBetween(pageReturn, "<span class='p'> ", "</span>");//音标信息
            String audioUrl = StringUtils.substringBetween(pageReturn, "ssplay('", "')");
            //            System.out.println(pageToken);
            //            System.out.println(yinbiao);
            Header[] cookieHeaders = response.getHeaders("Set-Cookie");
            StringBuilder cookieResult = new StringBuilder();
            if (cookieHeaders != null && cookieHeaders.length > 0)
            {
                for (Header header : cookieHeaders)
                {
                    String headerValue = header.getValue();
                    cookieResult.append(headerValue.substring(0, headerValue.indexOf(";"))).append(";");
                }
            }
            if (StringUtils.isEmpty(cookieResult.toString()) || StringUtils.isEmpty(pageToken))
            {
                return null;
            }
            String json = getExplainJson(text, cookieResult.toString(), pageToken);
            //            System.out.println("the json is: " + json);
            if (StringUtils.isEmpty(json))
            {
                return null;
            }
            
            JSONObject jsonObject = new JSONObject(json);
            String fanyi = jsonObject.optString("e");//翻译
            if (StringUtils.isEmpty(fanyi))
            {
                //连翻译都没有，则的确什么也没有！
                return null;
            }
            String liju = jsonObject.optString("s");//例句
            String bianhua = jsonObject.optString("t");//变化
            //            String askingSimilar = jsonObject.optString("g");//你可能在寻找  "atop"...
            
            DictCnTrans dictCnTrans = new DictCnTrans();
            dictCnTrans.setSrc(text);
            dictCnTrans.setYinbiao(yinbiao);
            dictCnTrans.setAudioUrl(audioUrl);
            //对翻译中的尖括号作特殊处理
            fanyi = handleSpecialJiankuohao(fanyi);
            dictCnTrans.setFanyi(fanyi);
            if (StringUtils.isNotEmpty(liju))
            {
                liju = removeAllBtwn(liju, "<a", "</a>");
                dictCnTrans.setLiju(liju);
            }
            if (StringUtils.isNotEmpty(bianhua))
            {
                dictCnTrans.setBianhua(bianhua);
            }
            dictCnTrans.setNetFail(false);
            return dictCnTrans;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DictCnTrans dictCnTrans = new DictCnTrans();
            dictCnTrans.setNetFail(true);//标记了网络请求失败了这一情况
            return dictCnTrans;
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }
    
    /**
     * 处理特殊的尖括号(特殊尖括号替换成转义字符)
     * @author nan.li
     * @param str
     * @return
     */
    private static String handleSpecialJiankuohao(String str)
    {
        //搜索尖括号，判定尖括号后面的字符，如果是<br      <font     </font    这样的，就不管；其余的情况，需要将其替换成&lt; &gt;
        
        if (StringUtils.indexOf(str, "<") == -1)
        {
            //不含有任何尖括号
            return str;
        }
        else
        {
            int fromIndex = 0;//从首字符开始搜索
            while (StringUtils.indexOf(str, "<", fromIndex) != -1)
            {
                int index = StringUtils.indexOf(str, "<", fromIndex);
                String houMian = str.substring(index);
                if (houMian.startsWith("<br") || houMian.startsWith("<font") || houMian.startsWith("</font"))
                {
                    //原封不动，直接搜索下一个左尖括号即可
                    fromIndex = index + 1;
                    continue;
                }
                else
                {
                    //需要进行尖括号转换
                    str = replaceFirstFromIndex(str, "<", "&lt;", fromIndex);
                    str = replaceFirstFromIndex(str, ">", "&gt;", fromIndex);
                }
            }
            return str;
        }
    }
    
    /**
     * 从某个字符开始替换首次出现的第一个字符
     * @author nan.li
     * @param str
     * @param string
     * @param string2
     * @param fromIndex
     * @return
     */
    private static String replaceFirstFromIndex(String str, String oldW, String newW, int fromIndex)
    {
        String left = str.substring(0, fromIndex);
        String right = str.substring(fromIndex);//老的右边
        right = right.replaceFirst(oldW, newW);//替换成新的右边
        return new StringBuilder(left).append(right).toString();
    }
    
    /**
     * 清空指定标记
     * @author nan.li
     * @param liju
     * @param string
     * @param string2
     * @return
     */
    private static String removeAllBtwn(String src, String fromTag, String toTag)
    {
        StringBuilder resBuilder = new StringBuilder();
        while (StringUtils.indexOf(src, fromTag) != -1 && StringUtils.indexOf(src, toTag) != -1)
        {
            resBuilder.append(StringUtils.substring(src, 0, StringUtils.indexOf(src, fromTag)));
            src = StringUtils.substring(src, (StringUtils.indexOf(src, toTag) + toTag.length()));
        }
        resBuilder.append(src);
        return resBuilder.toString();
    }
    
    public static String getExplainJson(String src, String cookie, String pageToken)
    {
        HttpClient httpClient = HttpClientKit.getDefaultHttpClient();
        String result = null;
        try
        {
            HttpPost httppost = new HttpPost("http://apii.dict.cn/ajax/dictcontent.php");
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("q", src));
            formparams.add(new BasicNameValuePair("s", "2"));
            formparams.add(new BasicNameValuePair("t", calcQueryKey(src, pageToken)));
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            httppost.setHeader("Cookie", cookie);
            //            System.out.println("executing request " + httppost.getURI());
            HttpResponse response = httpClient.execute(httppost);
            result = EntityUtils.toString(response.getEntity(), UTF8_ENCODING);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }
    
    /**
     * 算出查询的秘钥
     * @author nan.li
     * @param src
     * @param pageToken
     * @return
     */
    private static String calcQueryKey(String src, String pageToken)
    {
        // 创建脚本引擎管理器
        ScriptEngineManager factory = new ScriptEngineManager();
        // 创建JavaScript引擎
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // 从字符串中赋值JavaScript脚本
        try
        {
            String js = StringUtils.replace(Constant.JS_TEMPLATE, "$$pagetoken$$", pageToken);
            js = StringUtils.replace(js, "$$src$$", src);
            return engine.eval(js).toString();
        }
        catch (ScriptException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
    public static void main(String[] args)
    {
        
        //        Constant.JS_TEMPLATE =
        //            IOUtils.toString(LoadTemplateConfig.class.getClassLoader().getResourceAsStream(Constant.JS_TPL_CONFIG), Constant.UTF8_ENCODING);
        //        System.out.println(JsUtils.invoke("c:\\1.js", "tk", "good"));
        
        //        System.out.println(TranslateUtil.translate("好人", Language.ENGLISH.getValue()));
        //        System.out.println(translateYoudao("平安", Language.CHINA.getValue(), Language.ENGLISH.getValue()));
        
        //        System.out.println(TranslateUtil.translate("good", Language.CHINA.getValue()));
        //        System.out.println(TranslateUtil.translate("hadoop", Language.CHINA.getValue()));
        //        System.out.println(TranslateUtil.translate("good people think further", Language.CHINA.getValue()));
        
        //        System.out.println(TranslateUtil.translate("真不错哦", Language.ENGLISH.getValue()));
        //        System.out.println(TranslateUtil.translate("今天心情好爽！", Language.ENGLISH.getValue()));
        //        System.out.println(TranslateUtil.translate("中文", Language.ENGLISH.getValue()));
        System.out.println(TranslateUtil.translateDictCn("cache"));
        System.out.println(TranslateUtil.translateDictCn("fund"));
        //        System.out.println(TranslateUtil.translateDictCn("hadoop"));
        //        System.out.println(translateGoogle("good", Language.ENGLISH.getValue(), Language.CHINA.getValue()));
        //        System.out.println(translateGoogle("好人", Language.CHINA.getValue(), Language.ENGLISH.getValue()));
        //        System.out.println(translateGoogle("monster", Language.ENGLISH.getValue(), Language.CHINA.getValue()));
        //        System.out.println(translateGoogle("Monster", Language.ENGLISH.getValue(), Language.CHINA.getValue()));
    }
}