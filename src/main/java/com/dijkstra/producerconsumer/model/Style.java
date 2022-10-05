package com.dijkstra.producerconsumer.model;

/**
 * provides functions to generate styles
 */
public class Style {
  /**
   * generate a style from key/value
   *
   * @param key
   * @param value
   * @return a string that represents the style
   */
  public static String from(String key, String value) {
    return String.format("%s: %s;", key, value);
  }
}
