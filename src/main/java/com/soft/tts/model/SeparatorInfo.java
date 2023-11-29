package com.soft.tts.model;

import static com.soft.tts.model.Messages.SEPARATOR_CANNOT_BE_NULL;

/** Class represents a container for holding a separator and its index inside a sentence. */
public class SeparatorInfo {
  private final String separator;
  private final int index;

  public SeparatorInfo(String separator, int index) {
    if (separator == null) {
      throw new IllegalArgumentException(SEPARATOR_CANNOT_BE_NULL);
    }
    this.separator = separator;
    this.index = index;
  }

  public String getSeparator() {
    return separator;
  }

  public int getIndex() {
    return index;
  }
}
