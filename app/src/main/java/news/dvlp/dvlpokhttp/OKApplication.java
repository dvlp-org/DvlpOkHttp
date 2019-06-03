package news.dvlp.dvlpokhttp;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.HashMap;
import java.util.Map;

import news.dvlp.http.Interceptor.HttpLoggingInterceptor;
import news.dvlp.http.RetrofitManager;


/**
 * 创建时间：2018/8/2
 * 编写人： karler
 * 功能描述：
 */
public class OKApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy
//                .newBuilder()
//                .logStrategy(new LogCatStrategy())
//                .tag("OKHTTP_LOG")
//                .methodCount(1).showThreadInfo(false).build()) {
//            @Override
//            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
//            }
//        });
//
//
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Logger.d(message);
//            }
//        });
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wanandroid.com/")
                .callFactory(new OkHttpClient.Builder()
                        .addNetworkInterceptor(httpLoggingInterceptor)
                        .build())
                .addCallAdapterFactory(ExecutorCallAdapterFactory.INSTANCE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManager.init(retrofit);*/
//        RetrofitManager.initJSonTag("code","msg","data",0+"");
//        RetrofitManager.initHeadParam(getBaseParams());
        RetrofitManager.init(Api.API_BASE);

    }
    private static Map<String, Object> getBaseParams() {

        // 拼接参数
        Map<String, Object> headMap = new HashMap();
        headMap.put("appFlag", "APP_FLAG");
        headMap.put("channel", "APP_FLAG");
        headMap.put("channelId", "10");
        headMap.put("clientId", "clientId");
        headMap.put("deviceId", "clientId");
        headMap.put("deviceName", "clientId");
        headMap.put("orgId", "orgId");
        String version = "clientId";
        version = version.replace("v", "");//和ios保持统一，去掉V
        headMap.put("version", version);
        headMap.put("systemType", "systemType");
        headMap.put("token", "token");

        return headMap;
    }

    public static class LogCatStrategy implements LogStrategy {

        @Override
        public void log(int priority, @Nullable String tag, @NonNull String message) {
            Log.println(priority, randomKey() + tag, message);
        }

        private int last;

        private String randomKey() {
            int random = (int) (10 * Math.random());
            if (random == last) {
                random = (random + 1) % 10;
            }
            last = random;
            return String.valueOf(random);
        }
    }
}
