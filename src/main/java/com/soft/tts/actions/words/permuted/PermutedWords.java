package com.soft.tts.actions.words.permuted;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.soft.tts.model.Messages.REQUIRED_WORD;

/**
 * Class processes sentences by processing each word, maintaining the first and last characters
 * unchanged while randomly permuting the remaining characters in the word.
 */
public class PermutedWords extends SentenceManager<String> implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "permutedWords";

  private final List<SentenceHolder> tokens;

  public PermutedWords(List<SentenceHolder> tokens) {
    this.tokens = tokens;
  }

  @Override
  public String get() {
    ExecutorService service = Executors.newFixedThreadPool(1);

    CompletableFuture<List<String>> result;
    try {
      List<CompletableFuture<String>> listSentenceFutures = applyAction(tokens, service, 0);
      result = allOfFutures(listSentenceFutures);
    } catch (Exception e) {
      logException(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      service.shutdown();
    }

    return extractResult(result);
  }

  @Override
  public String performAction(SentenceHolder sentence) {
    logAction(ACTION_IDENTIFIER);
    logAction(Thread.currentThread().toString());
    return this.processSentence(sentence);
  }

  private String processSentence(SentenceHolder sentence) {
    String[] words = sentence.getSentence().trim().split("\\s+");

    StringJoiner result = new StringJoiner(DEFAULT_DELIMITER);
    for (String word : words) {
      result.add(processWord(word));
    }

    return result.toString().concat(sentence.getSeparator());
  }

  private String processWord(String word) {
    if (word.length() == 0) {
      throw new IllegalArgumentException(REQUIRED_WORD);
    }
    if (word.length() < 4) {
      return word;
    }
    return word.charAt(0)
        + permuteWord(word.substring(1, word.length() - 1))
        + word.substring(word.length() - 1);
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
