package com.soft.tts.actions;

import com.soft.tts.model.SentenceHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * An abstract class designed as the parent for managing and processing actions on linguistic units,
 * providing a unified framework for handling both words and sentences. The BaseActionManager serves
 * as an overarching abstraction, offering common functionality and structure for subclasses. It
 * introduces the {@code allOfFutures} method that allows the aggregation of results from a list of
 * CompletableFuture instances, providing a convenient way to manage asynchronous operations.
 *
 * The class also defines the {@code applyAction} method, which is responsible for applying a specified
 * processing action to a list of linguistic units, represented by SentenceHolder objects. This method
 * utilizes an ExecutorService for parallel execution and introduces a simulated delay specified by the
 * 'timeInSecondToSimulateLoad' parameter, allowing control over the processing time for each linguistic unit.
 *
 * Additionally, the class includes the {@code submitTasks} method, which submits processing tasks for a
 * list of linguistic units to be executed asynchronously using a specified number of threads. This method
 * manages the execution of processing tasks, collects their results, and returns a CompletableFuture
 * containing the aggregated results.
 *
 * @param <T> The type of result produced by the linguistic unit processing action.
 */
public abstract class BaseActionManager<T> {

  public abstract List<CompletableFuture<T>> applyAction(
      List<SentenceHolder> tokens, ExecutorService service, int timeInSecondToSimulateLoad);

  public CompletableFuture<List<T>> submitTasks(List<SentenceHolder> tokens, int numOfThreads) {
    ExecutorService service = Executors.newFixedThreadPool(numOfThreads);

    CompletableFuture<List<T>> result;
    try {
      List<CompletableFuture<T>> listSentenceFutures = applyAction(tokens, service, 0);
      result = allOfFutures(listSentenceFutures);

    } catch (Exception e) {
      logException(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      service.shutdown();
    }
    return result;
  }

  public <T> CompletableFuture<List<T>> allOfFutures(List<CompletableFuture<T>> futuresList) {
    CompletableFuture<Void> allFuturesResult =
        CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]));
    return allFuturesResult.thenApply(
        v -> futuresList.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
  }

  public abstract void logException(String action);
}
