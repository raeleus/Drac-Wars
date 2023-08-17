package com.ray3k.template;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;

import java.net.URLEncoder;
import java.security.MessageDigest;

import static text.formic.Stringf.format;

public class GameJoltApi {
    public static void addHighScore(long score, String name) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(HttpMethods.GET);
        String gameID = Gdx.files.internal("secret/gameid").readString();
        String key = Gdx.files.internal("secret/key").readString();
        String url = "https://api.gamejolt.com/api/game/v1_2/scores/add/";
        String content = "?game_id=" + gameID + "&guest=" + urlEncode(name) + "&score=" + format("$%,d", score) + "&sort=" + score;
        String signature = encrypt(url + content + key);
        httpRequest.setUrl(url + content + "&signature=" + signature);
        
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("handled");
            }
            
            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                System.out.println("failed to send score");
            }
            
            @Override
            public void cancelled() {
                System.out.println("cancelled");
            }
        });
    }
    
    public static MessageDigest crypt;
    
    public static String encrypt(String message) {
        crypt.reset();
        try {
            final byte[] bytes = message.getBytes("UTF-8");
            final byte[] digest = crypt.digest(bytes);
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
        
        }
        return null;
    }
    
    private static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
