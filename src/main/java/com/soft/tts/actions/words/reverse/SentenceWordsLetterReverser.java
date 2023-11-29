package com.soft.tts.actions.words.reverse;

import com.soft.tts.actions.words.WordManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.function.Supplier;

/** Class processes sentences by reversing the letters of each word. */
public class SentenceWordsLetterReverser extends WordManager<String> implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "SentenceWordsLetterReverser";

  private final List<SentenceHolder> tokens;
  private final Integer load;

  public SentenceWordsLetterReverser(List<SentenceHolder> tokens, Integer load) {
    this.tokens = tokens;
    this.load = load;
  }

  @Override
  public String get() {
    return extractResult(submitTasks(tokens, tokens.size(), load));
  }

  @Override
  public String performAction(String word) {
    logAction(ACTION_IDENTIFIER);
    return reverseWord(word);
  }

  private String reverseWord(String word) {
    if (WordManager.containsComma(word)) {
      return new StringBuilder(word.substring(0, word.length() - 1))
          .reverse()
          .append(COMMA)
          .toString();
    }
    return new StringBuilder(word).reverse().toString();
  }
}
