package com.example.bootjedis.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
    private Integer roomTag;
    /**
     * 真人 + 假人
     */
    private String audienceAmount;
    /**
     * 上一小时排名
     */
    private String lastRankNum;

    /**
     * 开启赏金时间
     */
    private String awawdTime;

    /**
     * 剩余的空位
     */
    private Integer awardLeftNum;

    /**
     * 包间内用户(真人)
     */
    private List<Integer> userIds;

    /**
     * 房间类型 '0: 普通用户创建房间；1:  官方运营房间; 2：测试房间'
     */
    private Integer type;


    private ThreadLocal<Integer> finalScoreLocal = ThreadLocal.withInitial(() -> 0);


    public Integer getScore() {
        return this.finalScoreLocal.get();
    }

    public int getUserIdSize() {
        if (userIds == null) {
            return 0;
        }else {
            return this.userIds.size();
        }
    }

}
