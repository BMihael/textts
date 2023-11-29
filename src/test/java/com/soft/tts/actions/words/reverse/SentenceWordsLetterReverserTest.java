package com.soft.tts.actions.words.reverse;

import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SentenceWordsLetterReverserTest extends TestCase {

  private static final String text = "Coolest ship ever. Is sun biggest? Elephant is large";
  private static final String textWordsReversed =
      "TselooC pihs reve. SI nus tseggib? TnahpelE si egral.";

  private static final String text_with_comma =
      "Coolest ship ever, Is sun biggest? Elephant is large";
  private static final String textWordsReversed_with_comma =
      "TselooC pihs reve, sI nus tseggib? TnahpelE si egral.";

  SentenceWordsLetterReverser sentenceWordsLetterReverser;

  @Test
  public void testGet() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text);
    sentenceWordsLetterReverser = new SentenceWordsLetterReverser(tokens);
    assertEquals(textWordsReversed, sentenceWordsLetterReverser.get());
  }

  @Test
  public void testGet_with_comma() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_with_comma);
    sentenceWordsLetterReverser = new SentenceWordsLetterReverser(tokens);
    assertEquals(textWordsReversed_with_comma, sentenceWordsLetterReverser.get());
  }
}
