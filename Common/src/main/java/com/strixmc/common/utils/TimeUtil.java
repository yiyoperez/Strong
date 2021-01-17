package com.strixmc.common.utils;

import java.text.DecimalFormat;

public final class TimeUtil {

  private static final String HOUR_FORMAT = "%02d:%02d:%02d";
  private static final String MINUTE_FORMAT = "%02d:%02d";

  public static String millisToTimer(long millis) {
    long seconds = millis / 1000L;

    if (seconds > 3600L) {
      return String.format(HOUR_FORMAT, seconds / 3600L, seconds % 3600L / 60L, seconds % 60L);
    } else {
      return String.format(MINUTE_FORMAT, seconds / 60L, seconds % 60L);
    }
  }

  public static String millisToSeconds(long millis) {
    return new DecimalFormat("#0.0").format(millis / 1000.0F);
  }

  public static String millisToRoundedTime(long millis) {
    millis += 1L;

    long seconds = millis / 1000L;
    long minutes = seconds / 60L;
    long hours = minutes / 60L;
    long days = hours / 24L;
    long weeks = days / 7L;
    long months = weeks / 4L;
    long years = months / 12L;

    if (years > 0) {
      return years + "y";
    } else if (months > 0) {
      return months + "M";
    } else if (weeks > 0) {
      return weeks + "w";
    } else if (days > 0) {
      return days + "d";
    } else if (hours > 0) {
      return hours + "h";
    } else if (minutes > 0) {
      return minutes + "m";
    } else {
      return seconds + "s";
    }
  }

}