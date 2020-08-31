package com.example.bootjedis.mapper;

import com.example.bootjedis.pojo.ChannelData;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChannelDataMapper {

    /**
     * 根据主键查询
     */
    ChannelData selectByPrimaryKey(Integer id);


    /**
     * 根据主键更新
     */
    int updateByPrimaryKey(ChannelData record);

  /**
   * 查询数据库是否存在同样数据
     * @param product
   * @param channel
   * @param popularizeDate
   * @return
   */
    int checkExist(@Param("product") String product, @Param("channel") String channel, @Param("popularizeDate") String popularizeDate);

    /**
     * 新增插入
     * @param record
     * @return
     */
    int insert(ChannelData record);

    /**
     * 渠道更新后联动录入数据表更新，1是原来数据值，2是新值
     * @param product1
     * @param channel1
     * @param product2
     * @param channel2
     * @return
     */
    int updateByProductAndChannel(@Param("product1") String product1, @Param("channel1") String channel1, @Param("product2") String product2, @Param("channel2") String channel2);

    /**
     * 根据查询条件返回所有符合条件的数据
     * @param product
     * @param channel
     * @param popularizeDate
     * @param userName
     * @return
     */
    List<ChannelData> selectAll(@Param("product") String product, @Param("channel") String channel, @Param("popularizeDate") String popularizeDate, @Param("userName") String userName, @Param("pageNum")int pageNum, @Param("size")int size);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(@Param("ids")List<String> ids);

    /**
     * 根据主键获取数据
     * @param ids
     * @return
     */
    List<ChannelData> selectByIds(@Param("ids")List<String> ids);

    /**
     * 返回总条数
     * @param userName
     * @return
     */
    int selectAllByAccount(@Param("userName") String userName);

}