package com.soft.tts.util;

import com.soft.tts.exception.NoTextDetected;
import com.soft.tts.exception.SentenceUtilsException;
import com.soft.tts.model.SentenceHolder;

import java.util.*;

import static com.soft.tts.model.Messages.NO_TEXT_DETECTED;
import static com.soft.tts.model.Messages.UTIL_CLASS_INSTANTIATION;

/** Utility class for separating text into tokens representing sentences */
public class SentenceUtil {

  private static final String DEFAULT_SEPARATOR = ".";
  private static final Set<String> separators = new HashSet<>();

  static {
    separators.add(".");
    separators.add("?");
    separators.add("!");
  }

  private SentenceUtil() {
    throw new SentenceUtilsException(UTIL_CLASS_INSTANTIATION);
  }

  public static List<SentenceHolder> generateTokens(String text) {
    if (text == null || text.length() == 0) {
      throw new NoTextDetected(NO_TEXT_DETECTED);
    }
    return splitTextIntoTokens(new ArrayList<>(), text);
  }

  private static List<SentenceHolder> splitTextIntoTokens(
      List<SentenceHolder> tokens, String text) {
    Optional<SeparatorInfo> separatorInfoOptional = firstOccurrenceOfSentenceSeparator(text);

    if (separatorInfoOptional.isEmpty()) {
      if (text.length() != 0) {
        tokens.add(new SentenceHolder(DEFAULT_SEPARATOR, text.trim()));
      }
      return tokens;
    }

    SeparatorInfo separatorInfo = separatorInfoOptional.get();
    int indexOfSeparator = separatorInfo.getIndex();
    String temp = text.substring(0, indexOfSeparator);
    text = text.substring(indexOfSeparator + 1);
    tokens.add(new SentenceHolder(separatorInfo.getSeparator(), temp.trim()));
    return splitTextIntoTokens(tokens, text);
  }

  private static Optional<SeparatorInfo> firstOccurrenceOfSentenceSeparator(String text) {
    int minIndex = Integer.MAX_VALUE;
    String tempSeparator = DEFAULT_SEPARATOR;

    for (String separator : separators) {
      int index = text.indexOf(separator);
      if (index != -1 && index < minIndex) {
        minIndex = index;
        tempSeparator = separator;
      }
    }

    return minIndex != Integer.MAX_VALUE
        ? Optional.of(new SeparatorInfo(tempSeparator, minIndex))
        : Optional.empty();
  }
}
