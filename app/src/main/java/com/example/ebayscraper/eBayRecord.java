package com.example.ebayscraper;

public class eBayRecord {
  private String searchValue;
  private int totalCounted;
  private double totalAmount;
  private double historicalHigh;
  private double historicalLow;
  private double average;
  private double currentLow;
  private double currentHigh;

  // Getters
  public String getSearchValue() {
    return searchValue;
  }

  public int getTotalCounted() {
    return totalCounted;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public double getHistoricalHigh() {
    return historicalHigh;
  }

  public double getHistoricalLow() {
    return historicalLow;
  }

  public double getAverage() {
    return average;
  }

  public double getCurrentLow() {
    return currentLow;
  }

  public double getCurrentHigh() {
    return currentHigh;
  }

  // Setters

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  public void setTotalCounted(int totalCounted) {
    this.totalCounted = totalCounted;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void setHistoricalHigh(double historicalHigh) {
    this.historicalHigh = historicalHigh;
  }

  public void setHistoricalLow(double historicalLow) {
    this.historicalLow = historicalLow;
  }

  public void setAverage(double average) {
    this.average = average;
  }

  public void setCurrentLow(double currentLow) {
    this.currentLow = currentLow;
  }
  public void setCurrentHigh(double currentHigh) {
    this.currentHigh = currentHigh;
  }
}
