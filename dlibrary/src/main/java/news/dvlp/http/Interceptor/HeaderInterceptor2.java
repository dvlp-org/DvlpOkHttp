package news.dvlp.http.Interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class HeaderInterceptor2 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder requestBuilder  = originalRequest.newBuilder();
        originalRequest = requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8")
                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                        bodyToString(originalRequest.body())))//关键部分，设置requestBody的编码格式为json
                .build();
        return chain.proceed(originalRequest);
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
