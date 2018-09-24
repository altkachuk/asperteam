package com.cyplay.atproj.asperteam.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by andre on 12-Jul-18.
 */

public class NotificationSender extends AsyncTask<String, Void, JSONObject> {

    private final static String url = "https://fcm.googleapis.com/fcm/send";
    private final static String charset = "UTF-8";

    private String _serverKey;
    private JSONObject jObj;

    public NotificationSender(String serverKey) {
        _serverKey = serverKey;
    }

    public void run(String id, String to, String title, String body, String clickAction) {
        this.execute(id, to, title, body,clickAction);
    }

    @Override
    protected void onPreExecute() {
        ;
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        return sendNotification(args[0], args[1], args[2], args[3], args[4]);
    }

    protected void onPostExecute(JSONObject json) {
        ;
    }

    private JSONObject sendNotification(String id, String to, String title, String body, String clickAction) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("to", to);
        params.put("title", title);
        params.put("body", body);
        params.put("click_action", clickAction);

        return performPostCall(url, _serverKey, params);
    }

    private JSONObject performPostCall(String requestURL, String authToken, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Authorization", "key=" + authToken);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("to", postDataParams.get("to"));

            JSONObject notificationParam = new JSONObject();
            notificationParam.put("title", postDataParams.get("title"));
            notificationParam.put("body", postDataParams.get("body"));
            notificationParam.put("click_action", postDataParams.get("click_action"));
            jsonParam.put("notification", notificationParam);

            JSONObject dataParam = new JSONObject();
            dataParam.put("id", postDataParams.get("id"));
            jsonParam.put("data", dataParam);

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.write(jsonParam.toString().getBytes(charset));

            writer.flush();
            writer.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            jObj = new JSONObject(response.toString());
        }
        catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public interface OnMailgunListener {
        void onSend();
    }
}
