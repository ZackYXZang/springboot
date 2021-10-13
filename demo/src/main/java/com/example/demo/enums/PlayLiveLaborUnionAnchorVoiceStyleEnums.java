package com.example.demo.enums;


import lombok.Getter;

/**
 * 唱歌风格枚举
 */
@Getter
public enum PlayLiveLaborUnionAnchorVoiceStyleEnums {

  /**
   *
   */
  CURE_ACCOMPANY(1, "治愈/陪伴"),
  SWEET_CUTE(2, "甜美/可爱"),
  INTELLECTUALITY_ADULT(3, "知性/成熟/御姐"),
  SEXY(4, "性感/纯欲"),
  SOFT(5, "温柔/轻熟/清新"),
  NEUTRAL(6, "中性/大大咧咧"),
  SMOKY_SOUND(7, "烟嗓/慵懒"),
  YOUNG(8, "少年/活力/酷"),
  MATURE_UNCLE(9, "成熟大叔/故事性");

  private Integer code;
  private String comment;

  PlayLiveLaborUnionAnchorVoiceStyleEnums(Integer code, String comment) {
    this.code = code;
    this.comment = comment;
  }

  public static PlayLiveLaborUnionAnchorVoiceStyleEnums ofComment(String comment) {
    for (PlayLiveLaborUnionAnchorVoiceStyleEnums typeEnum : PlayLiveLaborUnionAnchorVoiceStyleEnums.values()) {
      if (typeEnum.getComment().equals(comment)) {
        return typeEnum;
      }
    }
    return null;
  }
}
