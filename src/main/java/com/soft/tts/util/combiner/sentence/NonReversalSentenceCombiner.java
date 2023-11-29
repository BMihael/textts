package com.soft.tts.util.combiner.sentence;

import static com.soft.tts.actions.sentence.SentenceManager.DEFAULT_DELIMITER;

public class NonReversalSentenceCombiner implements SentenceCombiner {

  @Override
  public String apply(String first, String second) {
    return first + DEFAULT_DELIMITER + second;
  }
}
