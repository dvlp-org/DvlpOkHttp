package news.dvlp.http.Converter;

/**
 * 普通的结果提示 ，code=0代表成功
 * Created by karler on 2017/9/26.
 */
public class Tip {
    private String code = "-1";
    private String msg;

    public Tip(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return code == 0+"";
    }
}
