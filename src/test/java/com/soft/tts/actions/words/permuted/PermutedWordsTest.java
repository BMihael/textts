package com.soft.tts.actions.words.permuted;

import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PermutedWordsTest extends TestCase {

  private static final String text = "Coolest ship ever.";
  private static final String text_with_comma =
      "Coolest ship ever. Elephant is large, is sun biggest?";

  PermutedWords permutedWords;

  @Test
  public void testGet() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text);
    permutedWords = new PermutedWords(tokens);
    String result = permutedWords.get();
    assertEquals(result.substring(0, 1), "C");
    assertEquals(result.substring(6, 7), "t");

    assertEquals(result.substring(8, 9), "s");
    assertEquals(result.substring(11, 12), "p");

    assertEquals(result.substring(13, 14), "e");
    assertEquals(result.substring(16, 17), "r");
  }

  @Test
  public void testGet_with_comma() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_with_comma);
    permutedWords = new PermutedWords(tokens);
    String result = permutedWords.get();
    assertEquals(result.substring(0, 1), "C");
    assertEquals(result.substring(6, 7), "t");

    assertEquals(result.substring(8, 9), "s");
    assertEquals(result.substring(11, 12), "p");

    assertEquals(result.substring(13, 14), "e");
    assertEquals(result.substring(16, 17), "r");
  }
}
