package com.example.bootjedis.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: liuchen
 * @date: 2021/5/18
 * @time: 11:14 上午
 * @Description: 服务端存储的房间特征
 */
@Data
@EqualsAndHashCode(of = "roomId")
public class KtvRoomFeatureEntity {


  /**
   * 房间id
   */
  private Integer roomId;

  /**
   * 房间模式
   *
   */
  private Integer mode;
  /**
   * 房间排麦用户 key userid value songid
   */
  private String miclist;

  /**
   * 房主设置
   *
   */
  private Integer recommend;

  // 是否pgc房间，0否，1是
  private Integer ispgc;

  /**
   * 房间标签
   *
   */
  private String tag;
  /**
   * 频道标签
   *
   */
  @JSONField(name = "room_tag")
  private Integer roomTag;
  /**
   * 真人 + 假人
   */
  @JSONField(name = "audience_amount")
  private Integer audienceAmount = 0;
  /**
   * 上一小时排名
   */
  @JSONField(name = "last_rank_num")
  private Integer lastRankNum;

  /**
   * 开启赏金时间
   */
  @JSONField(name = "awawd_time")
  private String awawdTime;

  /**
   * 剩余的空位
   */
  @JSONField(name = "award_left_num")
  private Integer awardLeftNum;
  /**
   * 赏金房间类型：0=免费赏金，1=百元赏金，2=千元赏金，3=自由千元赏金，4=自由万元赏金
   */
  @JSONField(name = "award_type")
  private Integer awardType;

  /**
   * 包间内用户(真人)
   */
  private List<Integer> userIds;

  /**
   * 房间类型 '0: 普通用户创建房间；1:  官方运营房间; 2：测试房间'
   */
  private Integer type;

  /**
   * 麦上人数
   */
  private Integer onmicnum;

  // 返回给服务端数据
  private JSONObject reServer;

  /**
   * 房主
   */
  private String owner;
  /**
   * 房间展示头像
   */
  private List<String> headphotos;
  /**
   * 房副
   */
  @JSONField(name = "vice_owner")
  private List<String> viceOwner;

  /**
   * https://wiki.changba.com/pages/viewpage.action?pageId=36238769
   * <p>
   * 是否挂机 0 未挂机 1 挂机
   *
   */

  @JSONField(name = "user_sing_option")
  private UserSingOption userSingOption;


  @JSONField(deserialize = false, serialize = false)
  private ThreadLocal<Integer> finalScoreLocal = ThreadLocal.withInitial(() -> 0);

  public int getUserIdSize() {
    if (userIds == null) {
      return 0;
    } else {
      return this.userIds.size();
    }
  }


  @Data
  private static class UserSingOption {

    //有可能为null
    @JSONField(name = "is_origin_music")
    private String originMusic;
    @JSONField(name = "is_audience_origin_music")
    private String audienceOriginMusic;

  }


}
