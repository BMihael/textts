package com.soft.tts.actions.sentence.reverse;

import com.soft.tts.actions.SentenceManager;
import com.soft.tts.model.SentenceHolder;

import java.util.List;
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
    return extractResult(submitTasks(tokens, 1));
  }

  @Override
  public String performAction(SentenceHolder sentence) {
    logAction(ACTION_IDENTIFIER);
    return reverseSentence(sentence);
  }

  private String reverseSentence(SentenceHolder sentence) {
    return sentence.getSentence() + sentence.getSeparator();
  }
}
