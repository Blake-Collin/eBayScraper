package com.example.ebayscraper;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  /**
   * Main onCreate method for our first & only activity
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Blank our Title & Value Fields to start.
    clearOutputFields();
  }

  
  /** clearOutputFields will clear all our code editable information. */
  protected void clearOutputFields() {
    ArrayList<TextView> tv = new ArrayList<>();
    tv.add((TextView) findViewById(R.id.textViewFieldTitle1));
    tv.add((TextView) findViewById(R.id.textViewFieldTitle2));
    tv.add((TextView) findViewById(R.id.textViewFieldTitle3));
    tv.add((TextView) findViewById(R.id.textViewFieldTitle4));
    tv.add((TextView) findViewById(R.id.textViewFieldTitle5));
    tv.add((TextView) findViewById(R.id.textViewFieldValue1));
    tv.add((TextView) findViewById(R.id.textViewFieldValue2));
    tv.add((TextView) findViewById(R.id.textViewFieldValue3));
    tv.add((TextView) findViewById(R.id.textViewFieldValue4));
    tv.add((TextView) findViewById(R.id.textViewFieldValue5));

    for (TextView tvi : tv) {
      tvi.setText("");
    }
  }

  public void onPress(View view) {

    Log.i(TAG, "Getting our search phrase");
    String searchPhrase = ((EditText) findViewById(R.id.editTextSearchValue)).getText().toString();
    if (!searchPhrase.equals("")) {

      Log.i(TAG, "Clear our output fields to start");
      clearOutputFields();
      ((TextView) findViewById(R.id.textViewFieldTitle1)).setText("Querying Please Wait...");

      Log.i(TAG, "Starting our new search");

      Log.i(TAG, "searchPhrase Set to: " + searchPhrase);
      String operation = null;

      // Can expand if more operations is needed
      if (((RadioButton) findViewById(R.id.radioButtonHistorical)).isChecked()) {
        operation = "historical";
      } else if (((RadioButton) findViewById(R.id.radioButtonScrap)).isChecked()) {
        operation = "scrap";
      }

      // Create and run our new thread.
      Log.i(TAG, "Starting thread eBay" + operation);
      ApplicationController applicationController =
          new ApplicationController(this, searchPhrase, operation);
      Thread thread = new Thread(applicationController, "eBay" + operation);
      thread.start();
    }
  }
}
