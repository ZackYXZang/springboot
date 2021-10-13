package com.example.demo.enums;


import lombok.Getter;

/**
 * 特长枚举
 */
@Getter
public enum PlayLiveLaborUnionAnchorSpecialtyEnums {

  /**
   *
   */
  SING(1, "唱歌"),
  CHAT(2, "聊天"),
  SING_CHAT(3, "唱聊"),
  NOTHING(4, "毫无特长");

  private Integer code;
  private String comment;

  PlayLiveLaborUnionAnchorSpecialtyEnums(Integer code, String comment) {
    this.code = code;
    this.comment = comment;
  }

  public static PlayLiveLaborUnionAnchorSpecialtyEnums ofComment(String comment) {
    for (PlayLiveLaborUnionAnchorSpecialtyEnums typeEnum : PlayLiveLaborUnionAnchorSpecialtyEnums.values()) {
      if (typeEnum.getComment().equals(comment)) {
        return typeEnum;
      }
    }
    return null;
  }
}
