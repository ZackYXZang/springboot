package com.example.demo.enums;


import lombok.Getter;

/**
 * 声音年龄枚举
 */
@Getter
public enum PlayLiveLaborUnionAnchorVoiceAgeEnums {

  /**
   *
   */
  OLD(1, "老年");

  private Integer code;
  private String comment;

  PlayLiveLaborUnionAnchorVoiceAgeEnums(Integer code, String comment) {
    this.code = code;
    this.comment = comment;
  }

  public static PlayLiveLaborUnionAnchorVoiceAgeEnums ofComment(String comment) {
    for (PlayLiveLaborUnionAnchorVoiceAgeEnums typeEnum : PlayLiveLaborUnionAnchorVoiceAgeEnums.values()) {
      if (typeEnum.getComment().equals(comment)) {
        return typeEnum;
      }
    }
    return null;
  }
}
