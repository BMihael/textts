package com.soft.tts.util;

import com.soft.tts.exception.NoTextDetected;
import com.soft.tts.model.SentenceHolder;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SentenceUtilTest extends TestCase {

  private static final String text1 = null;
  private static final String text2 = "";
  private static final String text3 = "Sentence without separator";
  private static final String text4 = "Sentence 1. Sentence 2.";
  private static final String text5 = "Sentence 1? Sentence 2.";
  private static final String text5_without_dot_at_end = "Sentence 1? Sentence 2";
  private static final String text6_sentence_with_comma = "Sentence 1, Sentence 2";

  @Test
  public void test1() {
    Assertions.assertThrows(
        NoTextDetected.class,
        () -> {
          SentenceUtil.generateTokens(text1);
        });
  }

  @Test
  public void test2() {
    Assertions.assertThrows(
        NoTextDetected.class,
        () -> {
          SentenceUtil.generateTokens(text2);
        });
  }

  @Test
  public void test3_not_null() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text3);
    for (SentenceHolder token : tokens) {
      Assertions.assertNotNull(token);
    }
  }

  @Test
  public void test3_specific_text() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text3);
    Assertions.assertEquals(tokens.get(0).getSeparator(), ".");
    Assertions.assertEquals(tokens.get(0).getSentence(), text3);
  }

  @Test
  public void test4_count() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text4);
    Assertions.assertEquals(tokens.size(), 2);
  }

  @Test
  public void test4_specific_text() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text4);
    Assertions.assertEquals(tokens.get(1).getSeparator(), ".");
    Assertions.assertEquals(tokens.get(1).getSentence(), "Sentence 2");
  }

  @Test
  public void test5_specific_text_different_separator() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text5);
    Assertions.assertEquals(tokens.get(0).getSeparator(), "?");
    Assertions.assertEquals(tokens.get(0).getSentence(), "Sentence 1");
  }

  @Test
  public void test5_without_dot_at_end() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text5_without_dot_at_end);
    Assertions.assertEquals(tokens.size(), 2);
  }

  @Test
  public void test5_without_dot_at_end_specific_text() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text5_without_dot_at_end);
    Assertions.assertEquals(tokens.get(1).getSeparator(), ".");
    Assertions.assertEquals(tokens.get(1).getSentence(), "Sentence 2");
  }

  @Test
  public void testIsSeparator() {
    assertTrue(SentenceUtil.isSeparator("."));
    assertTrue(SentenceUtil.isSeparator("?"));
    assertTrue(SentenceUtil.isSeparator("!"));
    assertFalse(SentenceUtil.isSeparator("m"));
  }

  @Test
  public void test_text6() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text6_sentence_with_comma);
    assertEquals(1, tokens.size());
  }
}
