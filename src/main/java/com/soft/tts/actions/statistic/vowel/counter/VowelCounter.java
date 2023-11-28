package com.soft.tts.actions.statistic.vowel.counter;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.actions.statistic.vowel.counter.model.VowelOccurrence;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** Class counts the number of vowels in each sentence. */
public class VowelCounter extends SentenceManager<VowelOccurrence>
    implements Supplier<List<VowelOccurrence>> {
  private static final String ACTION_IDENTIFIER = "VowelCounter";
  private static final String VOWELS = "aeiou";

  private final List<SentenceHolder> tokens;

  public VowelCounter(List<SentenceHolder> tokens) {
    this.tokens = tokens;
  }

  @Override
  public List<VowelOccurrence> get() {
    ExecutorService service = Executors.newFixedThreadPool(1);

    CompletableFuture<List<VowelOccurrence>> result;
    try {
      List<CompletableFuture<VowelOccurrence>> listSentenceFutures =
          applyAction(tokens, service, 0);
      result = allOfFutures(listSentenceFutures);
    } catch (Exception e) {
      logException(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      service.shutdown();
    }

    return extractResultOfVowelOccurrence(result);
  }

  @Override
  public VowelOccurrence performAction(SentenceHolder sentence) {
    logAction(ACTION_IDENTIFIER);
    logAction(Thread.currentThread().toString());
    return this.countVowels(sentence);
  }

  private List<VowelOccurrence> extractResultOfVowelOccurrence(
      CompletableFuture<List<VowelOccurrence>> future) {
    List<VowelOccurrence> result = null;
    try {
      result = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return result;
  }

  public VowelOccurrence countVowels(SentenceHolder sentences) {
    return new VowelOccurrence(
        sentences.getSentence() + sentences.getSeparator(),
        countVowelOccurrences(sentences.getSentence()));
  }

  private Map<Character, Integer> countVowelOccurrences(String sentence) {
    return sentence
        .toLowerCase()
        .chars()
        .filter(c -> VOWELS.indexOf(c) != -1)
        .boxed()
        .collect(Collectors.toMap(k -> (char) k.intValue(), v -> 1, Integer::sum));
  }
}
