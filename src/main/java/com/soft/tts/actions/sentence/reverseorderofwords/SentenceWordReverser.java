package com.soft.tts.actions.sentence.reverseorderofwords;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/** Class reverses the order of words within a sentence. */
public class SentenceWordReverser extends SentenceManager<String> implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "sentenceWordReverser";

  private final List<SentenceHolder> tokens;

  public SentenceWordReverser(List<SentenceHolder> tokens) {
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
