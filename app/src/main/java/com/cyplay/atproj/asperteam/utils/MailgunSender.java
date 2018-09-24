package com.cyplay.atproj.asperteam.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by andre on 16-May-18.
 */

public class MailgunSender extends AsyncTask<String, Void, JSONObject> {

    private static final String mailgun_url = "https://api.mailgun.net/v3/sandbox67f400dc98dc44efbce59090353e3ff5.mailgun.org/messages";
    private static final String auth_token = "YXBpOmtleS02YjViMTVjNmJkYTg2ZmM3YWY5NDU4ZGRjZjczZjk3Zg==";

    private JSONObject jObj;
    private OnMailgunListener _listener;

    public MailgunSender() {
        ;
    }

    public void run(String from, String to, String subject, String text, OnMailgunListener listener) {
        _listener = listener;
        this.execute(from, to, subject, text);
    }

    public void run(String from, String to, String cc, String subject, String text, OnMailgunListener listener) {
        _listener = listener;
        this.execute(from, to, cc, subject, text);
    }

    @Override
    protected void onPreExecute() {
        ;
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        if (args.length == 4)
            return sendMessage(args[0], args[1], args[2], args[3]);
        else if (args.length == 5)
            return sendMessage(args[0], args[1], args[2], args[3], args[4]);

        return null;
    }

    protected void onPostExecute(JSONObject json) {
        if (_listener != null)
            _listener.onSend();
    }

    private JSONObject sendMessage(String from, String to, String subject, String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", from);
        params.put("to", to);
        params.put("subject", subject);
        params.put("text", text);

        return performPostCall(mailgun_url, auth_token, params);
    }

    private JSONObject sendMessage(String from, String to, String cc, String subject, String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", from);
        params.put("to", to);
        params.put("cc", cc);
        params.put("subject", subject);
        params.put("text", text);

        return performPostCall(mailgun_url, auth_token, params);
    }

    private JSONObject performPostCall(String requestURL, String authToken, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + authToken);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
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
