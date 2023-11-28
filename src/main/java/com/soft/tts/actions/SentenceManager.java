package com.soft.tts.actions;

import com.soft.tts.actions.sentence.reverse.SentenceReverser;
import com.soft.tts.model.SentenceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An abstract class designed for managing and processing sentences. It provides a framework for
 * performing actions on individual sentences and handling asynchronous operations. Concrete
 * subclasses are expected to implement the {@code performAction} method to define specific sentence
 * processing logic.
 *
 * @param <T> The type of result produced by the sentence processing action.
 */
public abstract class SentenceManager<T> {

  protected static final String DEFAULT_DELIMITER = " ";

  private static final Logger logger = Logger.getLogger(SentenceReverser.class.getName());

  public abstract T performAction(SentenceHolder sentence);

  public List<CompletableFuture<T>> applyAction(
      List<SentenceHolder> tokens, ExecutorService service, int timeInSecondToSimulateLoad) {
    List<CompletableFuture<T>> list = new ArrayList<>();
    for (SentenceHolder token : tokens) {
      CompletableFuture<T> task =
          CompletableFuture.supplyAsync(
              () -> {
                try {
                  Thread.sleep(timeInSecondToSimulateLoad);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                return performAction(token);
              },
              service);
      list.add(task);
    }
    return list;
  }

  public <T> CompletableFuture<List<T>> allOfFutures(List<CompletableFuture<T>> futuresList) {
    CompletableFuture<Void> allFuturesResult =
        CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]));
    return allFuturesResult.thenApply(
        v -> futuresList.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
  }

  protected String extractResult(CompletableFuture<List<String>> future) {
    List<String> list;
    String resultString = null;
    try {
      list = future.get();
      resultString =
          list.stream().reduce((partial, next) -> partial + next + DEFAULT_DELIMITER).orElse("");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return resultString;
  }

  protected String capitaliseFirstWord(String word) {
    return Character.toUpperCase(word.charAt(0)) + word.substring(1);
  }

  public void logAction(String action) {
    logger.log(Level.INFO, action);
  }

  public void logException(String action) {
    logger.log(Level.SEVERE, action);
  }
}
