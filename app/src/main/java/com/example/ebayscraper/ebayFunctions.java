package com.example.ebayscraper;

import android.app.Activity;
import java.lang.ref.WeakReference;

public interface ebayFunctions {

  void process(String searchPhrase, WeakReference<Activity> activity);

}
