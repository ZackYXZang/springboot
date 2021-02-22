package com.example.bootlettuce.config;

/**
 * @author yuxiangzang
 * @create 2020-08-19-3:40 下午
 * @desc 伴奏推荐理由
 **/
public enum AccompanyReasonEnums {

  SELECTED_SONG("pool1", "已点歌曲"),
  SIMILAR_SONG("pool2", "已点歌曲的相似歌曲"),
  SELECTED_ARTIST("pool3", "已点艺术家"),
  SIMILAR_ARTIST("pool4", "已点艺术家的相似艺术家"),
  YEAR_SONG("pool5", "年代对应的歌曲"),
  STYLE_SONG("pool6", "小众曲风对应的歌曲"),
  LANGUAGE_SONG("pool7", "小众语种对应的歌曲"),
  HOT_SONG("pool8", "热门歌曲"),
  HOT_ARTIST("pool9", "热门艺术家"),
  FOLLOW_SONG("pool10", "关注用户唱过歌曲"),
  SIMILAR_USER_SONG("pool11", "相似用户唱过歌曲"),
  AGE_SONG("pool12", "年龄性别对应的歌曲"),
  ASCEND_FASTEST_SONG("pool13", "上升最快热门池");

  private String id;
  private String comment;

  AccompanyReasonEnums(String id, String comment) {
    this.id = id;
    this.comment = comment;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
