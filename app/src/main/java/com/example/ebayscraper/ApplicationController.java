package com.example.ebayscraper;

import android.app.Activity;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ApplicationController implements Runnable {

  private static final String TAG = "ApplicationController";
  private WeakReference<Activity> activity;
  private String searchPhrase;
  private String operation;
  private HashMap<String, ebayFunctions> runs = createMap();

  private HashMap<String, ebayFunctions> createMap() {
    HashMap<String, ebayFunctions> map = new HashMap<String, ebayFunctions>();
    map.put("scrap", new ebayScrap());
    map.put("historical", new ebayHistorical());
    return map;
  }

  public ApplicationController(Activity activity, String searchPhrase, String operation) {
    Log.i(TAG, "New Application Controller created");
    this.activity = new WeakReference<>(activity);
    this.searchPhrase = searchPhrase;
    this.operation = operation;
  }

  @Override
  public void run() {
    Log.i(TAG, "Starting our new thread");

    if (activity != null) {
      Log.i(TAG, "Activity still active run our operation");
      ebayFunctions newFunction = this.runs.get(this.operation);
      newFunction.process(this.searchPhrase, this.activity);
      Log.i(TAG, "Operation finished");
    }
  }
}
