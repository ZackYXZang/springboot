package com.example.bootjedis.utils;

import java.lang.Character.UnicodeBlock;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sxk 创建日期：2020/5/19
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

  private static Set<UnicodeBlock> mChineseUnicodeBlocks = new HashSet<UnicodeBlock>() {{
    add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
    add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
    add(UnicodeBlock.GENERAL_PUNCTUATION);
    add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
    add(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
  }};


  public static boolean isChineseChar(char c) {
    if (mChineseUnicodeBlocks.contains(UnicodeBlock.of(c))) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 如果中文字符多于非中文就认为是中文歌
   */
  public static boolean isChineseSong(String value) {
    if (isEmpty(value)) {
      return false;
    }
    int chineseCount = 0;
    int noChineseCount = 0;
    for (char c : value.toCharArray()) {
      if (' ' == c) {
        continue;
      }
      if (isChineseChar(c)) {
        chineseCount++;
      } else {
        noChineseCount++;
      }
    }
    return chineseCount > noChineseCount;
  }
}
