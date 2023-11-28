package com.soft.tts.model;

/** Utility class for storing constant messages. */
public class Messages {
  /** Message indicating that instantiation is not allowed for a utility class. */
  public static final String UTIL_CLASS_INSTANTIATION =
      "Instantiation not allowed for utility class.";

  /** Message indicating that it is required to have a minimum of one character. */
  public static final String REQUIRED_WORD = "It is required to have a minimum of one character.";

  /** Message indicating that no text was detected. */
  public static final String NO_TEXT_DETECTED = "No text was detected.";

  /** Message indicating that string representing sentence and separator must be present. */
  public static final String SENTENCE_HOLDER_FIELDS_CANNOT_BE_NULL =
      "Sentence and separator cannot be null";

  /** Message indicating that string representing separator cannot be null. */
  public static final String SEPARATOR_CANNOT_BE_NULL = "Separator cannot be null";

  /** Message indicating that string representing sentence and vowel must be present. */
  public static final String VOWEL_OCCURRENCE_FIELDS_CANNOT_BE_NULL =
      "Sentence and vowel occurrences cannot be null";
}
