package Spaceboom.API;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FUNCTION {


    public static JSONObject SaveScore(String formData){
        return API.POST_request(USER.username + "/"+USER.password+"/projeler/icerik-kaydet/0/",formData);
    }


    public static CompletableFuture<JSONObject> SaveScore2(String formData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return API.sendPostRequest(USER.username + "/"+USER.password+"/projeler/icerik-kaydet/0/",formData);
            } catch (IOException e) {
                e.printStackTrace();
                return new JSONObject().put("durum", 0).put("aciklama", "Sunucuya bağlanılamadı!");
            }
        });
    }
    public static CompletableFuture<JSONObject> Login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return API.sendRequest(username + "/" + password + "/0/0/0/");
            } catch (IOException e) {
                e.printStackTrace();
                return new JSONObject().put("durum", 0).put("aciklama", "Sunucuya bağlanılamadı!");
            }
        });
    }


}
