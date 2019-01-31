package news.dvlp.dvlpokhttp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import news.dvlp.dvlpokhttp.entity.Article;
import news.dvlp.dvlpokhttp.entity.LoginInfo;
import news.dvlp.dvlpokhttp.entity.WXArticle;
import news.dvlp.http.Callback.Call2;
import news.dvlp.http.Callback.Callback2;
import news.dvlp.http.Callback.CallbackAnim;
import news.dvlp.http.Callback.HttpError;
import news.dvlp.http.ControllerView.ILoadingView;
import news.dvlp.http.Converter.FileConverterFactory;
import news.dvlp.http.RequsetManager.CallManager;
import news.dvlp.http.RetrofitManager;
import news.dvlp.http.progress.ProgressInterceptor;
import news.dvlp.http.progress.ProgressListener;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;


public class MainActivity extends Activity implements ILoadingView {
    TextView progressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = findViewById(R.id.progress);
        progressView.setTextColor(Color.RED);

        //下载权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }



    public void login(View view) {
        RetrofitManager.create(ApiService.class)
                .getLogin("singleman", "123456")
                .enqueue(hashCode(), new CallbackAnim<LoginInfo>(this) {
                    @Override
                    public void onError(Call2<LoginInfo> call2, HttpError error) {
                        Toast.makeText(MainActivity.this, error.msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Call2<LoginInfo> call2, LoginInfo response) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void wxarticle(View view) {
        RetrofitManager.create(ApiService.class)
                .getWXarticle()
                .enqueue(hashCode(), new CallbackAnim<List<WXArticle>>(this) {
                    @Override
                    public void onError(Call2<List<WXArticle>> call2, HttpError error) {
                        Toast.makeText(MainActivity.this, error.msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Call2<List<WXArticle>> call2, List<WXArticle> response) {
                        Toast.makeText(MainActivity.this, "获取公众号列表成功", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void article0(View view) {
        RetrofitManager.create(ApiService.class)
                .getArticle0()
                .enqueue(hashCode(), new CallbackAnim<Article>(this) {
                    @Override
                    public void onError(Call2<Article> call2, HttpError error) {
                        Toast.makeText(MainActivity.this, error.msg, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(Call2<Article> call2, Article response) {
//                        Article article= JSON.parseObject(response,Article.class);
                        Toast.makeText(MainActivity.this, "获取公众号列表成功", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    /***
     * 组装签名和通道信息--open api
     * @param listMap 为比财数据map
     * @return 为包装后结果数据  Map<String, Object>
     */
    public static Map<String, Object> httpRequest(Map<String, Map<String, Object>> listMap) {
        Map<String, Object> mapParams = new HashMap<>();
        try { // 组装数据
            mapParams.put("biz_data", listMap);
            mapParams.put("channel_id", "");
            //加签
            mapParams.put("sign_data", "");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return mapParams;
    }
    /***
     * 组装签名和通道信息--open api
     * @param listMap 为比财数据map
     * @return 为包装后结果数据 JSON String
     */
    public static String httpRequestJson(Map<String, Map<String, Object>> listMap) {
        return JSON.toJSONString(httpRequest(listMap));
    }


    private static Map<String, Object> getBaseParams() {

        // 拼接参数
        Map<String, Object> headMap = new HashMap();
        headMap.put("appFlag", "APP_FLAG");
        headMap.put("channel", "APP_FLAG");
        headMap.put("channelId", "10");
        headMap.put("clientId", "");
        headMap.put("deviceId", "");
        headMap.put("deviceName", "");
        headMap.put("orgId", "orgId");
        String version = "";
        version = version.replace("v", "");//和ios保持统一，去掉V
        headMap.put("version", version);
        headMap.put("systemType", "systemType");
        headMap.put("token", "token");

        return headMap;
    }


    public void articleBc(View view) {
        String bodyStr="{\"head\":{\"appFlag\":\"BC\",\"channel\":\"Aiqiyi\",\"channelId\":\"1\",\"clientId\":\"10\",\"deviceId\":\"866656036074682\",\"deviceName\":\"xiaomi\",\"orgId\":\"70\",\"systemType\":\"android\",\"token\":\"BC-b892a1730e0e4578aea53a7caa004021\",\"version\":\"3.0.6\"},\"param\":{\"balance\":1.2,\"city\":\"北京\",\"createTime\":\"2018-05-22 09:43:25\",\"phoneNum\":\"13488898841\",\"sex\":\"男\"}}";
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);


        //parm
        Map<String, String> paramMap = new HashMap();
        paramMap.put("ORG_ID", "11111");


        Map<String, Map<String, Object>> listMap = new HashMap();
        listMap.put("head", getBaseParams());
//        listMap.put("param", paramMap);

        RetrofitManager.create(ApiService.class)
                .getBicai(paramMap)
                .enqueue(hashCode(), new CallbackAnim<String>(this) {
                    @Override
                    public void onError(Call2<String> call2, HttpError error) {
                        Toast.makeText(MainActivity.this, error.msg, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(Call2<String> call2, String response) {
//                        Article article= JSON.parseObject(response,Article.class);
                        Toast.makeText(MainActivity.this, "获取公众号列表成功", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    static final String TAG_LOAD_APK = "loadApk";

    private String range="0";
    public void download(View view) {


        final Button button = (Button) view;
        if (button.getText().equals("取消下载")) {
            CallManager.getInstance().cancel(TAG_LOAD_APK);
            return;
        }


//        String filePath = new File(getApplicationContext().getExternalCacheDir(), "test_douyin.jpg").getPath();
        String filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator, "test_douyin.apk").getPath();
//        String filePath = Environment.getExternalStorageDirectory() + "/myapp/";
        //构建可以监听进度的client
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(getProgressInterceptor()).build();

        //构建可以下载文件的client
        Retrofit retrofit = RetrofitManager.retrofit()
                .newBuilder()
                .callFactory(client)
                .addConverterFactory(new FileConverterFactory(filePath))
                .build();

        retrofit.create(ApiService.class)
                .loadDouYinApk(range)
                .enqueue(TAG_LOAD_APK, new Callback2<File>() {
                    @Override
                    public void onStart(Call2<File> call2) {
                        super.onStart(call2);
                        Log.e("contentLength-star","range==="+range+"---------------------------------");

                        button.setText("取消下载");
                    }

                    @Override
                    public void onError(Call2<File> call2, HttpError error) {
                        progressView.setText("下载进度:0");
                        Toast.makeText(MainActivity.this, error.msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Call2<File> call2, File response) {
                        installApk(response,getApplicationContext());
                    }

                    @Override
                    public void onCancel(Call2<File> call2) {
                        super.onCancel(call2);
                        progressView.setText("下载进度:0");

                        button.setText("下载抖音apk文件");
                    }

                    @Override
                    public void onCompleted(Call2<File> call2) {
                        super.onCompleted(call2);
                        button.setText("下载完成");
                    }
                });
    }

    /**
     * 安装apk ,兼容6.0问题
     */
    public static void installApk(File result, Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName()+".fileprovider" + "", result);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            mContext.startActivity(intent);
        } else {
            // apk下载完成后，调用系统的安装方法
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
            mContext.startActivity(intent);
        }

    }
    private ProgressInterceptor getProgressInterceptor() {
        return new ProgressInterceptor(new ProgressListener() {
            @Override
            public void onUpload(Request request, long progress, long contentLength, boolean done) {

            }

            @Override
            public void onDownload(Request request, final long progress, final long contentLength, boolean done) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("contentLength","range==="+range+"-----progress"+progress);
                        range=progress+"";
                        progressView.setText("下载进度:"+((int) (progress * 100f / contentLength)));

                    }
                });
            }
        });
    }

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //hashCode() 能保证唯一性，取消当前页面所发起的所有请求，只要
        // enqueue(tag, callback2) 传入的是对应的hashCode() 即可
        CallManager.getInstance().cancel(hashCode());
        //取消下载文件
        CallManager.getInstance().cancel(TAG_LOAD_APK);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}