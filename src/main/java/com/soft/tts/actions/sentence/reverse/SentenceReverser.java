package com.soft.tts.actions.sentence.reverse;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/** Class reverses sentences of given text. */
public class SentenceReverser extends SentenceManager<String> implements Supplier<String> {
  private static final String ACTION_IDENTIFIER = "sentenceReverser";

  private final List<SentenceHolder> tokens;

  public SentenceReverser(List<SentenceHolder> tokens) {
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

    return extractResultOfSentenceReversal(result);
  }

  @Override
  public String performAction(SentenceHolder sentence) {
    logAction(ACTION_IDENTIFIER);
    logAction(Thread.currentThread().toString());
    return reverseSentence(sentence);
  }

  private String reverseSentence(SentenceHolder sentence) {
    return sentence.getSentence() + sentence.getSeparator();
  }

  private String extractResultOfSentenceReversal(CompletableFuture<List<String>> future) {
    List<String> list;
    String resultString = null;
    try {
      list = future.get();
      resultString =
          list.stream().reduce((partial, next) -> next + DEFAULT_DELIMITER + partial).orElse("");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return resultString;
  }
}
