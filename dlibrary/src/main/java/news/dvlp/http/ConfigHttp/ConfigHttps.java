package news.dvlp.http.ConfigHttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liubaigang on 2018/12/3.
 */

public class ConfigHttps {

    public static String codeTag;// json code key
    public static String msgTag; // json msg  key
    public static String dataTag;// json data key
    public static String successTag; // 默认0 成功


    public static Map<String, Object> headParam=new HashMap<>(); //请求 头部的参数
    public static String bodyParam; //请求 body的参数



}
