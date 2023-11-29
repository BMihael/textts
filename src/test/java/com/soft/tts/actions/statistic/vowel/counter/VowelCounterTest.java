package com.soft.tts.actions.statistic.vowel.counter;

import com.soft.tts.actions.sentence.vowel.counter.VowelCounter;
import com.soft.tts.actions.sentence.vowel.model.VowelOccurrence;
import com.soft.tts.model.SentenceHolder;
import com.soft.tts.util.SentenceUtil;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class VowelCounterTest extends TestCase {

  private static final String text = "Coolest ship ever.";
  private static final String text_with_comma =
      "Coolest ship ever. Elephant is large, is sun biggest?";

  VowelCounter vowelCounter;

  @Test
  public void test1() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text);
    vowelCounter = new VowelCounter(tokens, 0);
    List<VowelOccurrence> lista = vowelCounter.get();
    assertNotNull(lista);
  }

  @Test
  public void test2() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text);
    vowelCounter = new VowelCounter(tokens, 0);
    List<VowelOccurrence> lista = vowelCounter.get();
    assertEquals("Coolest ship ever.", lista.get(0).getSentence());

    Map<Character, Integer> mapa = lista.get(0).getVowelOccurrence();

    Assertions.assertNull(mapa.get('a'));
    Assertions.assertEquals(mapa.get('e'), Integer.valueOf(3));
    Assertions.assertEquals(mapa.get('i'), Integer.valueOf(1));
    Assertions.assertEquals(mapa.get('o'), Integer.valueOf(2));
  }

  @Test
  public void test3() {
    List<SentenceHolder> tokens = SentenceUtil.generateTokens(text_with_comma);
    vowelCounter = new VowelCounter(tokens, 0);
    assertNotNull(vowelCounter.get());
  }
}
