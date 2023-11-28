package com.soft.tts.actions.words.reverse;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/** Class processes sentences by reversing the letters of each word. */
public class SentenceWordsLetterReverser extends SentenceManager<String>
    implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "SentenceWordsLetterReverser";

  private final List<SentenceHolder> tokens;

  public SentenceWordsLetterReverser(List<SentenceHolder> tokens) {
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
    return new StringBuilder(word).reverse().toString();
  }
}
