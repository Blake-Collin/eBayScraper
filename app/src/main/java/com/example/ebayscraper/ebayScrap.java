package com.example.ebayscraper;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class ebayScrap implements ebayFunctions {

  private static DecimalFormat df2 = new DecimalFormat("0.00");
  private static final String TAG = "ebayScrap";
  private static final String URL =
      "http://10.0.2.2:8080/ebayScraperServlet_war_exploded/ebayHelperServlet";

  @Override
  public void process(final String searchPhrase, final WeakReference<Activity> activity) {
    Log.i(TAG, "Starting our new scrap thread");

    // Callout to servlet
    Log.i(TAG, "Contacting Servlet");
    ebayServletCaller task = new ebayServletCaller();

    // Get our new values
    String myValue = null;
    String processURL = this.URL + "?&operation=scrap&search=" + searchPhrase.replace(" ", "%20");
    Log.i(TAG, "Test: " + processURL);
    try {
      myValue = task.execute(new String[] {processURL}).get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //Json to ebayRecord object here and then setup our view
    Gson gson = new Gson();
    eBayRecord record = null;
    record = gson.fromJson(myValue, eBayRecord.class);


    Log.i(TAG, "Sending to display");
    // Output to display
    final eBayRecord finalRecord = record;
    activity
        .get()
        .runOnUiThread(
            new Runnable() {
              @Override
              public void run() {
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle1)).setText("Item:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue1))
                    .setText(finalRecord.getSearchValue());
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle2))
                    .setText("Current High:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue2))
                    .setText("$" + df2.format(finalRecord.getCurrentHigh()));
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle3))
                    .setText("Current Low:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue3))
                    .setText("$" + df2.format(finalRecord.getCurrentLow()));
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle4))
                    .setText("Total Count:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue4))
                    .setText(Integer.toString(finalRecord.getTotalCounted()));
              }
            });
  }
}
