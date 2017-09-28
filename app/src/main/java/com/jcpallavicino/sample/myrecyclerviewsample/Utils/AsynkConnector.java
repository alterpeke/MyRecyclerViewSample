package com.jcpallavicino.sample.myrecyclerviewsample.Utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsynkConnector extends AsyncTask<String, String, String> {


    private final String endpoint = "https://jsonplaceholder.typicode.com/";

    private final String append = "photos";

    static final String COOKIES_HEADER = "Set-Cookie";

/*
    public static final int WEATHER = 0;

    private static final String weather_append = "data/2.5/weather";
*/

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";


    private Callback callback;
    private String content;
    private int service;
    private String param;

    private String url;
    private String method = GET;

    private int responseCode;

    public AsynkConnector(int service, String param, String content, Callback callback){

        this.callback = callback;
        this.content = content;
        this.service = service;
        this.param = param;

        url = composeURL(service, param);

    }


    private String composeURL(int service, String param) {
        String ret = null;
        ret = endpoint+param+append;
        method = GET;
        return ret;
    }


    public String doConnection(){

        String ret = null;

        try {

            //create URL object
            URL obj = new URL(this.url);
            //create the connection
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            //configure the connection

            //set cookies if NOT login

            conn.setUseCaches(false);
            conn.setDoInput(true);
            //conn.setDoOutput(true);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type","application/json");

            //content to byte array
            byte[]body = this.content.getBytes("UTF-8");
            //OutputStream from connection
            if(method.equalsIgnoreCase(POST) || method.equalsIgnoreCase(PUT)){
                OutputStream os = conn.getOutputStream();
                os.write(body);
                os.close();
            }

            //Get return values
            responseCode = conn.getResponseCode();
            InputStream is;
            if(responseCode == 200){
                is = conn.getInputStream();
            }else{
                is = conn.getErrorStream();
            }

            //turn the InputStream to String
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb= new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }


            br.close();
            is.close();
            conn.disconnect();

            ret = sb.toString();

        } catch (MalformedURLException e) {
            responseCode = -10;
            ret = e.getMessage();
        } catch (IOException e) {
            responseCode = -10;
            ret = e.getMessage();
        } catch (Exception e){
            responseCode = -10;
            ret = e.getMessage();
        }

        return ret;
    }



    @Override
    protected String doInBackground(String... strings) {
        return doConnection();
    }

    @Override
    protected void onPostExecute(String result) {
        callback.completed(result, responseCode);
    }


    @Override
    protected void onPreExecute() {
        callback.starting();
    }


    @Override
    protected void onProgressUpdate(String... text) {
        callback.update();

    }
}
