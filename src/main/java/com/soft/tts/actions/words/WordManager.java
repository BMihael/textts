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
import java.util.stream.Collectors;

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
                  logAction(Thread.currentThread().toString());
                  try {
                    Thread.sleep(load);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
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
      resultString = combineSeparatedSentences(separateSentences(list));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return resultString;
  }

  private static String combineSeparatedSentences(List<List<String>> list1) {
    return list1.stream().flatMap(List::stream).collect(Collectors.joining(" "));
  }

  private List<List<String>> separateSentences(List<String> list) {
    List<List<String>> result = new ArrayList<>();
    List<String> inner = new ArrayList<>();

    boolean wordShouldBeCapitalised = true;

    for (int i = 0; i < list.size(); i++) {
      String word = list.get(i);
      if (wordShouldBeCapitalised) {
        inner.add(capitaliseFirstWord(word));
        wordShouldBeCapitalised = false;
        continue;
      }
      if (SentenceUtil.isSeparator(word)) {
        wordShouldBeCapitalised = true;
        String wordBeforeSeparator = list.get(i - 1);
        inner.set(inner.size() - 1, wordBeforeSeparator + word);
        result.add(inner);
        inner = new ArrayList<>();
        continue;
      }
      inner.add(list.get(i));
    }

    return result;
  }

  public static boolean containsComma(String word) {
    return word.contains(COMMA);
  }

  public void logAction(String action) {
    logger.log(Level.INFO, action);
  }

  public void logException(String action) {
    logger.log(Level.SEVERE, action);
  }
}
