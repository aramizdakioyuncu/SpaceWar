package Spaceboom.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class API {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static String convertToMD5(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] mdBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte mdByte : mdBytes) {
                sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isInternetReachable() {
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(10000); // 10 seconds timeout
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static JSONObject sendRequest(String url) throws IOException, JSONException {

        if (!isInternetReachable()) {
            return new JSONObject().put("durum", 0).put("aciklama", "Sunucuya bağlanılamadı! (INTERNET)");

        }


        url = SECURITY.SSL+SECURITY.HOST+"/botlar/"+SECURITY.KEY+"/"+url;
        System.out.println(url);
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);

            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }


    public static JSONObject sendPostRequest(String url, String formData) throws IOException {

        if (!isInternetReachable()) {
            return new JSONObject().put("durum", 0).put("aciklama", "Sunucuya bağlanılamadı! (INTERNET)");
        }


        url = SECURITY.SSL+SECURITY.HOST+"/botlar/"+SECURITY.KEY+"/"+url;
        System.out.println(url + " (POST) ");

        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = formData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return new JSONObject(response.toString());

        } catch (IOException e) {

            e.printStackTrace();
            return new JSONObject().put("durum", 0).put("aciklama", "Sunucuya bağlanılamadı!");

        } finally {
            connection.disconnect();
        }
    }



    public static JSONObject POST_request(String link, String formData){
        try {
           return sendPostRequest(link, formData);

        } catch (Exception e) {
            System.out.println("[ARMOYU] Sunucuya bağlanılamadı");
            return new JSONObject().put("durum", 0).put("aciklama", "Sunucuya bağlanılamadı!");
        }
    }
}
