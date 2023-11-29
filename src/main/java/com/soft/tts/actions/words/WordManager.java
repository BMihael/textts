package com.soft.tts.actions.words;

import com.soft.tts.actions.BaseActionManager;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class designed for managing and processing words of sentence. It provides a framework
 * for performing actions on individual word and handling asynchronous operations. Concrete
 * subclasses are expected to implement the {@code performAction} method to define specific word
 * processing logic.
 *
 * @param <T> The type of result produced by the sentence processing action.
 */
public abstract class WordManager<T> extends BaseActionManager<T> {

  private static final Logger logger = Logger.getLogger(WordManager.class.getName());

  public abstract T performAction(String word);

  public List<CompletableFuture<T>> applyAction(
      List<SentenceHolder> tokens, ExecutorService service, Integer load) {
    List<CompletableFuture<T>> list = new ArrayList<>();
    for (SentenceHolder token : tokens) {

      String[] words = token.getSentence().trim().split("\\s+");
      for (String word : words) {

        CompletableFuture<T> task =
            CompletableFuture.supplyAsync(
                () -> {
                  try {
                    Thread.sleep(load);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                  logAction(Thread.currentThread().toString());
                  return performAction(word);
                },
                service);
        list.add(task);
      }

      CompletableFuture<T> completedFuture =
          (CompletableFuture<T>) CompletableFuture.completedFuture(token.getSeparator());
      list.add(completedFuture);
    }
    return list;
  }

  protected String extractResult(CompletableFuture<List<String>> future) {
    List<String> list;
    String resultString = null;
    try {
      list = future.get();
      resultString =
          list.stream()
              .reduce((a, b) -> SentenceUtil.isSeparator(b) ? a + b : a + " " + b)
              .orElse("");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return resultString;
  }

  public static boolean containsComma(String word) {
    return word.contains(",");
  }

  public void logAction(String action) {
    logger.log(Level.INFO, action);
  }

  public void logException(String action) {
    logger.log(Level.SEVERE, action);
  }
}
