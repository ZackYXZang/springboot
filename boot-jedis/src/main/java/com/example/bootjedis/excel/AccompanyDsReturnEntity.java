package com.example.bootjedis.excel;

import java.util.Map;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-08-19-2:35 下午
 * @desc 伴奏（全站热门，全站艺术家）返回实体
 **/
@Data
public class AccompanyDsReturnEntity {

  /**
   * 歌曲id
   */
  String songId;

  /**
   * 歌曲信息
   */
  Map<String, String> songInfoMap;



}
