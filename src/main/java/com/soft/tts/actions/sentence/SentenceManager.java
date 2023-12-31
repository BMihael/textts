package com.soft.tts.actions.sentence;

import com.soft.tts.actions.BaseActionManager;
import com.soft.tts.actions.sentence.reverse.SentenceReverser;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.combiner.sentence.NonReversalSentenceCombiner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.BinaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class designed for managing and processing sentences. It provides a framework for
 * performing actions on individual sentences and handling asynchronous operations. Concrete
 * subclasses are expected to implement the {@code performAction} method to define specific sentence
 * processing logic.
 *
 * @param <T> The type of result produced by the sentence processing action.
 */
public abstract class SentenceManager<T> extends BaseActionManager<T> {

  private static final Logger logger = Logger.getLogger(SentenceReverser.class.getName());

  public abstract T performAction(SentenceHolder sentence);

  public List<CompletableFuture<T>> applyAction(
      List<SentenceHolder> tokens, ExecutorService service, Integer load) {
    List<CompletableFuture<T>> list = new ArrayList<>();
    for (SentenceHolder token : tokens) {
      CompletableFuture<T> task =
          CompletableFuture.supplyAsync(
              () -> {
                logAction(Thread.currentThread().toString());
                try {
                  Thread.sleep(load);
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

  protected String extractResult(CompletableFuture<List<String>> future) {
    return extractResult(future, new NonReversalSentenceCombiner());
  }

  protected String extractResult(
      CompletableFuture<List<String>> future, BinaryOperator<String> combiner) {
    List<String> list;
    String resultString = null;
    try {
      list = future.get();
      resultString = list.stream().reduce(combiner).orElse("");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return resultString;
  }

  public void logAction(String action) {
    logger.log(Level.INFO, action);
  }

  @Override
  public void logException(String action) {
    logger.log(Level.SEVERE, action);
  }
}
