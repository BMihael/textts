package com.soft.tts.util.combiner.sentence;

import static com.soft.tts.actions.sentence.SentenceManager.DEFAULT_DELIMITER;

public class ReversalSentenceCombiner implements SentenceCombiner {

  @Override
  public String apply(String first, String second) {
    return second + DEFAULT_DELIMITER + first;
  }
}
