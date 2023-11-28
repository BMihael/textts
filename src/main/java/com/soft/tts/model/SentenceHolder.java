package com.soft.tts.model;

import static com.soft.tts.model.Messages.SENTENCE_HOLDER_FIELDS_CANNOT_BE_NULL;

/** Class represents a container for holding a sentence and its separator. */
public class SentenceHolder {

  private final String separator;
  private final String sentence;

  public SentenceHolder(String separator, String value) {
    if (separator == null || value == null) {
      throw new IllegalArgumentException(SENTENCE_HOLDER_FIELDS_CANNOT_BE_NULL);
    }
    this.separator = separator;
    this.sentence = value;
  }

  public String getSeparator() {
    return separator;
  }

  public String getSentence() {
    return sentence;
  }
}
