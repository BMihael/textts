package com.soft.tts.actions.words.permuted;

import com.soft.tts.actions.words.WordManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static com.soft.tts.model.Messages.REQUIRED_WORD;

/**
 * Class processes sentences by processing each word, maintaining the first and last characters
 * unchanged while randomly permuting the remaining characters in the word.
 */
public class PermutedWords extends WordManager<String> implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "permutedWords";

  private final List<SentenceHolder> tokens;

  public PermutedWords(List<SentenceHolder> tokens) {
    this.tokens = tokens;
  }

  @Override
  public String get() {
    return extractResult(submitTasks(tokens, 1));
  }

  @Override
  public String performAction(String word) {
    logAction(ACTION_IDENTIFIER);
    return this.processWord(word);
  }

  private String processWord(String word) {
    if (word.length() == 0) {
      throw new IllegalArgumentException(REQUIRED_WORD);
    }
    if (word.length() < 4) {
      return word;
    }
    int length = 1;
    if (WordManager.containsComma(word)) {
      length++;
    }
    return word.charAt(0)
        + permuteWord(word.substring(1, word.length() - length))
        + word.substring(word.length() - length);
  }

  private String permuteWord(String word) {
    char[] letters = word.toCharArray();
    Random random = new Random();

    for (int i = letters.length - 1; i > 0; i--) {
      int index = random.nextInt(i + 1);
      char temp = letters[i];
      letters[i] = letters[index];
      letters[index] = temp;
    }
    return new String(letters);
  }
}
