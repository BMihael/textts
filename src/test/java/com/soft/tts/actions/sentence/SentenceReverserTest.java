package com.soft.tts.actions.sentence;

import com.soft.tts.actions.sentence.reverse.SentenceReverser;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
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

  private static final String text_for_reversal_with_comma =
      "Coolest ship ever, Is sun biggest? Elephant is large";
  private static final String text_reversed_with_comma =
      "Elephant is large. Coolest ship ever, Is sun biggest?";

  SentenceReverser sentenceReverser;

  @Test
  public void testGet() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_for_reversal);
    sentenceReverser = new SentenceReverser(tokens);
    assertEquals(text_reversed, this.sentenceReverser.get());
  }

  @Test
  public void testGetWithComma() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_for_reversal_with_comma);
    sentenceReverser = new SentenceReverser(tokens);
    assertEquals(text_reversed_with_comma, this.sentenceReverser.get());
  }

  @Test
  public void testPerformAction() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(sentence1_separator);
    sentenceReverser = new SentenceReverser(tokens);
    String result =
        this.sentenceReverser.performAction(
            new SentenceHolder(sentence1_separator, sentence1_sentence));
    assertEquals(sentence1_result, result);
  }

}
