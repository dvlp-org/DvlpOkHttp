package news.dvlp.http.Converter;

import com.alibaba.fastjson.JSON;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by liubaigang on 2018/12/3.
 */

public class FastJsonConverterFactory extends Converter.Factory {
    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type != File.class) {
            return new FastJsonResponseBodyConverter<>(type);
        }
        return null;
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换发送给服务器的数据
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type != File.class) {
            return new FastJsonRequestBodyConverter<>();
        }
        return null;

    }


}
