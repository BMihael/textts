package com.soft.tts.actions.sentence.reverseorderofwords;

import com.soft.tts.actions.sentence.SentenceManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Supplier;

/** Class reverses the order of words within a sentence. */
public class SentenceWordReverser extends SentenceManager<String> implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "sentenceWordReverser";

  private final List<SentenceHolder> tokens;
  private final Integer load;

  public SentenceWordReverser(List<SentenceHolder> tokens, Integer load) {
    this.tokens = tokens;
    this.load = load;
  }

  @Override
  public String get() {
    return extractResultNonReversed(submitTasks(tokens, tokens.size(), load));
  }

  @Override
  public String performAction(SentenceHolder sentence) {
    logAction(ACTION_IDENTIFIER);
    return this.reverseOrderOfWords(sentence);
  }

  private String reverseOrderOfWords(SentenceHolder sentence) {
    String[] words = sentence.getSentence().trim().split("\\s+");

    StringJoiner result = new StringJoiner(DEFAULT_DELIMITER);
    for (int i = words.length - 1; i >= 0; i--) {
      String word = words[i];
      if (i == words.length - 1) {
        word = capitaliseFirstWord(word);
      }
      result.add(word);
    }

    return result.toString().concat(sentence.getSeparator());
  }
}
