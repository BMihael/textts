package com.soft.tts.actions.statistic.vowel.counter;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.actions.statistic.vowel.counter.model.VowelOccurrence;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
    CompletableFuture<List<VowelOccurrence>> result = submitTasks(tokens, 1);
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
