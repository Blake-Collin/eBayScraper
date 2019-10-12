package com.example.ebayscraper;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class ebayHistorical implements ebayFunctions {

  private static DecimalFormat df2 = new DecimalFormat("#.00");
  private static final String TAG = "ebayHistorical";
  private static final String URL =
      "http://10.0.2.2:8080/ebayScraperServlet_war_exploded/ebayHelperServlet";

  @Override
  public void process(final String searchPhrase, final WeakReference<Activity> activity) {
    Log.i(TAG, "Starting our new historical thread");

    // Insert code here
    Log.i(TAG, "Contacting Servlet");
    ebayServletCaller task = new ebayServletCaller();

    String myValue = null;
    String processURL = this.URL + "?&operation=historical&search=" + searchPhrase.replace(" ", "%20");

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


    //Display
    Log.i(TAG, "Sending to display");
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
                    .setText("Historical Average:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue2))
                    .setText("$" + df2.format(finalRecord.getAverage()));
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle3))
                    .setText("Historical Low:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue3))
                    .setText("$" + df2.format(finalRecord.getHistoricalLow()));
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle4))
                    .setText("Historical High:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue4))
                    .setText("$" + df2.format(finalRecord.getHistoricalHigh()));
                ((TextView) activity.get().findViewById(R.id.textViewFieldTitle5))
                    .setText("Total Count:");
                ((TextView) activity.get().findViewById(R.id.textViewFieldValue5))
                    .setText(Integer.toString(finalRecord.getTotalCounted()));
              }
            });
  }
}
