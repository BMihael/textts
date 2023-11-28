package com.soft.tts.actions.words.reverse;

import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SentenceWordsLetterReverserTest extends TestCase {

  private static final String text = "Coolest ship ever.";
  private static final String textWordsReversed = "TselooC pihs reve.";

  SentenceWordsLetterReverser sentenceWordsLetterReverser;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text);
    sentenceWordsLetterReverser = new SentenceWordsLetterReverser(tokens);
  }

  @Test
  public void testGet() {
    assertEquals(textWordsReversed, sentenceWordsLetterReverser.get());
  }
}
