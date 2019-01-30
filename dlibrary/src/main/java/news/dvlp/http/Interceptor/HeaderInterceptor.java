package news.dvlp.http.Interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by liubaigang on 2019/1/29.
 */

/**
 * Retrofit 基本参数拦截器
 */
public class HeaderInterceptor implements Interceptor {

    /**
     * 请求方法-GET
     */
    private static final String REQUEST_METHOD_GET = "GET";

    /**
     * 请求方法POST
     */
    private static final String REQUEST_METHOD_POST = "POST";

    /**
     * 基础参数-平台号
     */
    private static final String REQUEST_COMMON_PARAM_PLATFORM = "2";

    /**
     * 基础参数-个体户Id
     */
    private static final String REQUEST_COMMON_PARAM_PERSONALID = "0";

    /**
     * 基础参数-未登录 userId
     */
    private static final String REQUEST_COMMON_PARAM_USERID_DEFAULT = "0";

    /**
     * 基础参数-未登录 token
     */
    private static final String REQUEST_COMMON_PARAM_TOKEN_DEFAULT = "";

    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取原先的请求对象
        Request request = chain.request();
        if (REQUEST_METHOD_GET.equals(request.method())) {
            request = addGetBaseParams(request);
        } else if (REQUEST_METHOD_POST.equals(request.method())) {
            request = addPostBaseParams(request);
        }
        return chain.proceed(request);
    }


    /**
     * 添加GET方法基础参数
     *
     * @param request
     * @return
     */
    private Request addGetBaseParams(Request request) {

        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("platform", REQUEST_COMMON_PARAM_PLATFORM)//平台号
                .addQueryParameter("version", "版本号")//版本号
                .addQueryParameter("enterpriseId", "qi")//企业Id
                .addQueryParameter("personalId", REQUEST_COMMON_PARAM_PERSONALID)//个体户id
                .build();


            httpUrl = httpUrl.newBuilder()
                    .addQueryParameter("userId", REQUEST_COMMON_PARAM_USERID_DEFAULT)
                    .addQueryParameter("token", REQUEST_COMMON_PARAM_TOKEN_DEFAULT)
                    .build();


        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            paramMap.put(nameList.get(i), httpUrl.queryParameterValue(i));
        }
        httpUrl = httpUrl.newBuilder().addQueryParameter("sign", "qianming").build();

        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    /**
     * 添加POST方法基础参数
     *
     * @param request
     * @return
     */
    private Request addPostBaseParams(Request request) {

        /**
         * request.body() instanceof FormBody 为true的条件为：
         * 在ApiService 中将POST请求中设置
         * 1，请求参数注解为@FieldMap
         * 2，方法注解为@FormUrlEncoded
         */
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder builder = new FormBody.Builder();

            for (int i = 0; i < formBody.size(); i++) {
                //@FieldMap 注解 Map元素中 key 与 value 皆不能为null,否则会出现NullPointerException
                if (formBody.value(i) != null)
                    builder.add(formBody.name(i), formBody.value(i));
            }

            builder
                    .add("platform", REQUEST_COMMON_PARAM_PLATFORM)//平台
                    .add("version", "")//版本号
                    .add("enterpriseId","")//企业Id
                    .add("personalId", REQUEST_COMMON_PARAM_PERSONALID);//个体户id



                builder
                        .add("userId", REQUEST_COMMON_PARAM_USERID_DEFAULT)
                        .add("token", REQUEST_COMMON_PARAM_TOKEN_DEFAULT);


            //添加签名
            Map<String, Object> paramMap = new HashMap<>();
            formBody = builder.build();
            for (int i = 0; i < formBody.size(); i++) {
                paramMap.put(formBody.name(i), formBody.value(i));
            }
            formBody = builder
                    .add("sign", "sign")
                    .build();

            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }

}
