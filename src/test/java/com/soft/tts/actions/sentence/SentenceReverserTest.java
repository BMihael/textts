package com.soft.tts.actions.sentence;

import com.soft.tts.actions.sentence.reverse.SentenceReverser;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SentenceReverserTest extends TestCase {

  private static final String sentence1_separator = "?";
  private static final String sentence1_sentence = "Sentence 1";
  private static final String sentence1_result = "Sentence 1?";

  private static final String text_for_reversal =
      "Coolest ship ever. Is sun biggest? Elephant is large";
  private static final String text_reversed =
      "Elephant is large. Is sun biggest? Coolest ship ever.";

  SentenceReverser sentenceReverser;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_for_reversal);
    sentenceReverser = new SentenceReverser(tokens);
  }

  @Test
  public void testGet() {
    assertEquals(text_reversed, this.sentenceReverser.get());
  }

  @Test
  public void testPerformAction() {
    String result =
        this.sentenceReverser.performAction(
            new SentenceHolder(sentence1_separator, sentence1_sentence));
    assertEquals(sentence1_result, result);
  }
}
