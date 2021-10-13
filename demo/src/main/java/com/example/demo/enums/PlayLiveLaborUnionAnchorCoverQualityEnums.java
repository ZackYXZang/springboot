package com.example.demo.enums;


import java.util.Objects;
import lombok.Getter;

/**
 * 封面图质量枚举
 */
@Getter
public enum PlayLiveLaborUnionAnchorCoverQualityEnums {

  /**
   *
   */
  NORMAL(1, "封面图一般"),
  GOOD(2, "封面图优质"),
  BAD(3, "封面图较差");

  private Integer code;
  private String comment;

  PlayLiveLaborUnionAnchorCoverQualityEnums(Integer code, String comment) {
    this.code = code;
    this.comment = comment;
  }
  public static PlayLiveLaborUnionAnchorCoverQualityEnums ofComment(String comment) {
    for (PlayLiveLaborUnionAnchorCoverQualityEnums typeEnum : PlayLiveLaborUnionAnchorCoverQualityEnums.values()) {
      if (typeEnum.getComment().equals(comment)) {
        return typeEnum;
      }
    }
    return null;
  }

}
