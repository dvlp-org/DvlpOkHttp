package news.dvlp.dvlpokhttp;

/**
 * Created by CoderLengary
 */


public class Api {

    //This is the base API.
//    public static final String API_BASE = "http://www.wanandroid.com/";
    public static final String API_BASE = "http://wanandroid.com/";

    //Get the article list
    public static final String getWXarticle = API_BASE + "wxarticle/chapters/json";

    //Get the categories
    public static final String CATEGORIES = API_BASE + "tree/json";

    //Get the banner
    public static final String BANNER = API_BASE + "banner/json";


    //Login
    public static final String LOGIN = API_BASE + "user/login/";

    //Register
    public static final String REGISTER = API_BASE + "user/register";


    //Search articles
    public static final String QUERY_ARTICLES = API_BASE + "article/query/";

    //Hot Key
    public static final String HOT_KEY = API_BASE + "hotkey/json";

    //Collect article
    public static final String COLLECT_ARTICLE = API_BASE + "lg/collect/";

    //Cancel collecting article
    public static final String CANCEL_COLLECTING_ARTICLE = API_BASE + "lg/uncollect_originId/";

    //Get the favorite articles
    public static final String GET_FAVORITE_ARTICLES = API_BASE + "lg/collect/list/";


}
