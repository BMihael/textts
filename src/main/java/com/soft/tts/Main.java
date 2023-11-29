package com.soft.tts;

import com.soft.tts.actions.sentence.reverse.SentenceReverser;
import com.soft.tts.actions.sentence.reverseorderofwords.SentenceWordReverser;
import com.soft.tts.actions.sentence.vowel.counter.VowelCounter;
import com.soft.tts.actions.sentence.vowel.model.VowelOccurrence;
import com.soft.tts.actions.words.permuted.PermutedWords;
import com.soft.tts.actions.words.reverse.SentenceWordsLetterReverser;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.text.TextMock;
import com.soft.tts.text.TextProvider;
import com.soft.tts.util.SentenceUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(getNumberOfThreads(args));
    try {
      startTasks(executorService);
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }
  }

  private static int getNumberOfThreads(String[] args) {
    if (args.length == 0) {
      return 5;
    }
    return Integer.parseInt(args[0]);
  }

  public static void startTasks(ExecutorService executorService)
      throws ExecutionException, InterruptedException {
    long milisStart = System.currentTimeMillis();

    TextProvider textProvider = new TextMock();
    String text = textProvider.provideText2();
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text);

    CompletableFuture<String> task1 =
        CompletableFuture.supplyAsync(() -> new PermutedWords(tokens).get(), executorService);

    CompletableFuture<String> task2 =
        CompletableFuture.supplyAsync(
            () -> new SentenceWordsLetterReverser(tokens).get(), executorService);

    CompletableFuture<String> task3 =
        CompletableFuture.supplyAsync(
            () -> new SentenceWordReverser(tokens).get(), executorService);

    CompletableFuture<String> task4 =
        CompletableFuture.supplyAsync(() -> new SentenceReverser(tokens).get(), executorService);

    CompletableFuture<List<VowelOccurrence>> task5 =
        CompletableFuture.supplyAsync(() -> new VowelCounter(tokens).get(), executorService);

    CompletableFuture<Void> allFuture = CompletableFuture.allOf(task1, task2, task3, task4, task5);
    allFuture.join();

    // Results
    System.out.println("Original:");
    System.out.println(text);
    System.out.println();

    System.out.println("Task1: " + task1.get());
    System.out.println("Task2: " + task2.get());
    System.out.println("Task3: " + task3.get());
    System.out.println("Task4: " + task4.get());

    System.out.println("Task5:");
    System.out.println("Vowels in each sentence:");
    List<VowelOccurrence> list = task5.get();
    for (VowelOccurrence token : list) {
      System.out.println(token.getSentence());
      System.out.println(token.getVowelOccurrence());
    }

    long milisEnd = System.currentTimeMillis();
    System.out.println("Time: " + (milisEnd - milisStart));
  }
}
