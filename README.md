# DvlpOkHttp
retrofit2 极简封装，极简接入。

[ ![Download](https://api.bintray.com/packages/dvlp-org/MyMoven/dvlpokhttp/images/download.svg?version=1.0.1) ](https://bintray.com/dvlp-org/MyMoven/dvlpokhttp/1.0.1/link)
 [![](https://jitpack.io/v/dvlp-org/DvlpOkHttp.svg)](https://jitpack.io/#dvlp-org/DvlpOkHttp)

 范例请求URL
 http://wanandroid.com/article/list/0/json"
 
 对应json
 {
    "data":{
        "curPage":1,
        "offset":0,
        "over":false,
        "pageCount":288,
        "size":20,
        "total":5757
    },
    "errorCode":0,
    "errorMsg":""
}



 --------------------------------------------------------start------------------------------------------------------------------------
 
1.初始化配置

     加依赖项
 
     implementation 'com.dvlp:dvlpokhttp:1.0.1'
     
    添加网路权限
 
    <uses-permission android:name="android.permission.INTERNET" />

    在Aplication进行初始化

    public static final String API_BASE = "http://wanandroid.com/";//正常在项目常量类 Api.class

    RetrofitManager.init(API_BASE);
    
2.定义请求接口 ApiService
    
      //获取首页文章列表
    @GET("article/list/0/json")
    Call2<Article> getArticle0(); //Article：与data数据对应的实体类
    
 3.发起请求 
   
    RetrofitManager.create(ApiService.class)
                .getArticle0()
                .enqueue(hashCode(), new CallbackAnim<Article>(this) {
                    @Override
                    public void onError(Call2<Article> call2, HttpError error) {
                        Toast.makeText(MainActivity.this, error.msg, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(Call2<Article> call2, Article article) {
                        Toast.makeText(MainActivity.this, "获取首页文章列表", Toast.LENGTH_SHORT).show();

                    }
                });
      
------------------------------------------------end-------------------------------------------------------------------------------




**高度扩展说明*

1.三种初始化方式，以及使用条件

项目中默认顶级json key分别是：errorCode、errorMsg、data

   RetrofitManager.init(API_BASE); 

---
项目中默认顶级json 与默认不匹配：Code、Msg、dataS。“0”对应Code 成功状态

   RetrofitManager.initJSonTag("Code","Msg","dataS",0);
   RetrofitManager.init(API_BASE); 

---
库中封装Retrofit不满足需求使用，支持 Retrofit 自定义

   RetrofitManager.init(retrofit)



---
2.请求主体 CallbackAnim、Callback2

 CallbackAnim：支持获取请求Loding状态
 需要实现ILoadingView接口
 
    @Override
    public void showLoading() {
       // 显示loding布局
    }

    @Override
    public void hideLoading() {
       //隐藏loding布局
    }
    
 Callback2:不支持Loding获取状态           
 
3.data 支持类型 T

  对象数组
  
     Call2<List<WXArticle>> getArticle0();
  
  ---
  对象
  
     Call2<Article> getArticle0();
   
   ---
  文件
  
     Call2<File> getArticle0();
     
   ---  
  字符串
  
     Call2<String> getArticle0();

