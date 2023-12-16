package Sariaboom.API;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FUNCTION {


    public static JSONObject SaveScore(String formData) throws IOException {

        JSONObject cevap = API.POST_request(USER.username + "/"+USER.password+"/projeler/icerik-kaydet/0/",formData);
        return cevap;


    }
}
