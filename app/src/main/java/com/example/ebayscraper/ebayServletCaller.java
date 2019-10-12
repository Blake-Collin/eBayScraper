package com.example.ebayscraper;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

class ebayServletCaller extends AsyncTask<String, Void, String> {

  private static final String TAG = "ebayServletCaller";

  @Override
  protected String doInBackground(String... urls) {
    String output = null;
    for (String url : urls) {
      output = getOutputFromUrl(url);
    }
    Log.i(TAG, "Return: " + output );
    return output;
  }

  private String getOutputFromUrl(String url) {
    Log.i(TAG, "get Output from URL running");
    StringBuffer output = new StringBuffer("");
    try {
      InputStream stream = getHttpConnection(url);
      BufferedReader buffer = new BufferedReader(
          new InputStreamReader(stream));
      String s = "";
      while ((s = buffer.readLine()) != null)
        output.append(s);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return output.toString();
  }

  //Get Method
  private InputStream getHttpConnection(String urlString)
      throws IOException {
    Log.i(TAG, "Getting HTTP Connection");
    InputStream stream = null;
    URL url = new URL(urlString);
    URLConnection con = url.openConnection();

    try {
      HttpURLConnection httpConnection = (HttpURLConnection)con;
      httpConnection.setRequestMethod("GET");
      httpConnection.connect();

      if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        stream = httpConnection.getInputStream();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return stream;
  }

  //Post Method
  private InputStream getHttpConnection2(String urlString)
      throws IOException {
    Log.i(TAG, "Getting HTTP Connection");

    String[] temp = urlString.split("[|]");
    for (String s : temp) {
      Log.i(TAG, s);
    }
    InputStream stream = null;
    URL url = new URL(temp[0]);
    URLConnection con = url.openConnection();

    //Prevent Caching
    con.setRequestProperty("Cache-Control", "no-cache");
    con.setDefaultUseCaches(false);
    con.setUseCaches(false);

    try {
      //Create our basic Post Request
      HttpURLConnection httpConnection = (HttpURLConnection)con;
      httpConnection.setRequestMethod("POST");
      httpConnection.setDoInput(true);

      //creating our simple form
      Map<String,String> arguments = new HashMap<>();
      arguments.put("operation", temp[1]);
      arguments.put("search", temp[2]);
      StringJoiner sj = new StringJoiner("&");
      for(Map.Entry<String,String> entry : arguments.entrySet())
        sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
            + URLEncoder.encode(entry.getValue(), "UTF-8"));
      byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
      int length = out.length;

      //Attach our form contents
      httpConnection.setFixedLengthStreamingMode(length);
      httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
      httpConnection.connect();
      try(OutputStream os = httpConnection.getOutputStream()) {
        os.write(out);
      }

      if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        stream = httpConnection.getInputStream();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return stream;
  }


}
