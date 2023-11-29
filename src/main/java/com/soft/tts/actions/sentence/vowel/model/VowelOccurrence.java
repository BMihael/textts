package com.soft.tts.actions.sentence.vowel.model;

import java.util.Map;

import static com.soft.tts.model.Messages.VOWEL_OCCURRENCE_FIELDS_CANNOT_BE_NULL;

/**
 * Class represents a container for holding a sentence and map of vowel occurrences. It is used to
 * associate a given sentence with the count of each vowel present in the sentence.
 */
public class VowelOccurrence {
  private final String sentence;
  private final Map<Character, Integer> vowelOccurrence;

  public VowelOccurrence(String sentence, Map<Character, Integer> vowelOccurrence) {
    if (sentence == null || vowelOccurrence == null) {
      throw new IllegalArgumentException(VOWEL_OCCURRENCE_FIELDS_CANNOT_BE_NULL);
    }
    this.sentence = sentence;
    this.vowelOccurrence = vowelOccurrence;
  }

  public String getSentence() {
    return sentence;
  }

  public Map<Character, Integer> getVowelOccurrence() {
    return vowelOccurrence;
  }
}
