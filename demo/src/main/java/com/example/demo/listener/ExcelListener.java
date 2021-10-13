package com.example.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.PlayLiveLaborUnionAnchorEntity;
import com.example.demo.entity.PlayLiveLaborUnionAnchorExcelEntity;
import com.example.demo.enums.PlayLiveLaborUnionAnchorCoverQualityEnums;
import com.example.demo.enums.PlayLiveLaborUnionAnchorSpecialtyEnums;
import com.example.demo.enums.PlayLiveLaborUnionAnchorVoiceAgeEnums;
import com.example.demo.enums.PlayLiveLaborUnionAnchorVoiceStyleEnums;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author yuxiangzang
 * @create 2020-11-12-4:39 下午
 * @desc 监听类
 **/
@Slf4j
public class ExcelListener extends AnalysisEventListener<PlayLiveLaborUnionAnchorExcelEntity> {

  private List<PlayLiveLaborUnionAnchorExcelEntity> datas = new ArrayList<>();

  @Override
  public void invoke(PlayLiveLaborUnionAnchorExcelEntity data, AnalysisContext context) {
    datas.add(data);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    log.info("upload all data size:{}", datas.size());
    datas.remove(0);

    List<PlayLiveLaborUnionAnchorEntity> result = datas.stream().map(x -> {
      PlayLiveLaborUnionAnchorEntity entity = new PlayLiveLaborUnionAnchorEntity();
      entity.setAnchorId(x.getAnchorId());
      entity.setNickName(x.getNickName());
      if (!StringUtils.isEmpty(x.getCoverQuality())) {
        entity.setCoverQuality(
            PlayLiveLaborUnionAnchorCoverQualityEnums.ofComment(x.getCoverQuality()).getCode());
      }

      if (!StringUtils.isEmpty(x.getVoiceAge())) {
        entity.setVoiceAge(
            PlayLiveLaborUnionAnchorVoiceAgeEnums.ofComment(x.getVoiceAge()).getCode());
      }

      if (!StringUtils.isEmpty(x.getSpecialty())) {
        entity.setSpecialty(
            PlayLiveLaborUnionAnchorSpecialtyEnums.ofComment(x.getSpecialty()).getCode());
      }

      List<Integer> voiceStyle = new ArrayList<>();

      if (!StringUtils.isEmpty(x.getCure())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getCure()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getSweet())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getSweet()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getZhixing())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getZhixing()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getSexy())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getSexy()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getSoft())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getSoft()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getNeutral())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getNeutral()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getSmoky())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getSmoky()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getYoung())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getYoung()).getCode());
      }
      
      if (!StringUtils.isEmpty(x.getMature())) {
        voiceStyle.add(PlayLiveLaborUnionAnchorVoiceStyleEnums.ofComment(x.getMature()).getCode());
      }

      entity.setVoiceStyle(voiceStyle);
      
      return entity;
    }).collect(Collectors.toList());
    String jsonResult = JSONObject.toJSONString(result);
    log.info(jsonResult);
    log.info("upload all result size:{}", result.size());


  }

  public List<PlayLiveLaborUnionAnchorExcelEntity> getDatas() {
    return datas;
  }
}
