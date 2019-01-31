package news.dvlp.dvlpokhttp;



import java.io.File;
import java.util.List;
import java.util.Map;

import news.dvlp.dvlpokhttp.entity.Article;
import news.dvlp.dvlpokhttp.entity.LoginInfo;
import news.dvlp.dvlpokhttp.entity.WXArticle;
import news.dvlp.http.Callback.Call2;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 创建时间：2018/4/8
 * 编写人： karler
 * 功能描述：测试接口
 */
public interface ApiService {
    //登录
    @FormUrlEncoded
    @POST("user/login")
    Call2<LoginInfo> getLogin(@Field("username") String username, @Field("password") String password);

    //获取微信公众号列表
    @GET(Api.getWXarticle)
    Call2<List<WXArticle>> getWXarticle();

    //获取首页文章列表
    @GET("article/list/0/json")
    Call2<Article> getArticle0();

    //获取首页文章列表
//    @GET("article/list/0/json")
//    Call2<String> getArticle0();

    //下载文件
    @GET(Api.BANNER)
    Call2<File> loadDouYinApk(@Header("Range") String range);


    //比财测试
//    @FormUrlEncoded
    @POST(Api.GET_BICAI)
    Call2<String> getBicai(@Body Map<String, String> map);

    @POST(Api.GET_BICAI)
    Call2<String> getBicai2(@Body Map<String, Map<String, Object>> map);
}


