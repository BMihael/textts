package com.soft.tts.actions.sentence;

import com.soft.tts.actions.sentence.reverseorderofwords.SentenceWordReverser;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SentenceWordReverserTest extends TestCase {

  private static final String sentence1_separator = "?";
  private static final String sentence1_sentence = "Sentence 1";
  private static final String sentence1_result = "1 Sentence?";

  private static final String text_for_reversal = "Elephant is large. Ship is cool";
  private static final String text_reversed = "Large is Elephant. Cool is Ship.";

  SentenceWordReverser sentenceWordReverser;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_for_reversal);
    sentenceWordReverser = new SentenceWordReverser(tokens);
  }

  @Test
  public void testGet() {
    assertEquals(text_reversed, this.sentenceWordReverser.get());
  }

  @Test
  public void testPerformAction() {
    String result =
        this.sentenceWordReverser.performAction(
            new SentenceHolder(sentence1_separator, sentence1_sentence));
    assertEquals(sentence1_result, result);
  }
}
