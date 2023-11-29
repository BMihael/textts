package com.soft.tts.actions.words.reverse;

import com.soft.tts.actions.sentence.SentenceManager;
import com.soft.tts.actions.words.WordManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Supplier;

/** Class processes sentences by reversing the letters of each word. */
public class SentenceWordsLetterReverser extends SentenceManager<String>
    implements Supplier<String> {
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
  public String performAction(SentenceHolder sentence) {
    logAction(ACTION_IDENTIFIER);
    return this.processSentence(sentence);
  }

  private String processSentence(SentenceHolder sentence) {
    String[] words = sentence.getSentence().trim().split("\\s+");

    StringJoiner result = new StringJoiner(DEFAULT_DELIMITER);
    for (int i = 0; i < words.length; i++) {
      String reversedWord = reverseWord(words[i]);
      if (i == 0) {
        result.add(capitaliseFirstWord(reversedWord));
        continue;
      }
      result.add(reversedWord);
    }

    return result.toString().concat(sentence.getSeparator());
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
