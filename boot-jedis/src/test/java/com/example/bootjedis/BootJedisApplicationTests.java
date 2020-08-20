package com.example.bootjedis;

import com.example.bootjedis.config.JedisConfig;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class BootJedisApplicationTests {

    @Autowired
    private JedisPool jedisPool;


    @Test
    void contextLoads() {
        List<String> list1 = new ArrayList<>(100);

        if (list1 == null) {
            System.out.println("null");
        }

        if (list1.size() == 0) {
            System.out.println("size is 0");
        }

        if (list1.size() == 100) {
            System.out.println("size is 100");
        }

        List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<30;i++){
            list.add(i);
        }

        for(int i = 0;i<10;i++){
            //显示数字并将其从列表中删除,从而实现不重复.
            System.out.println(new Random().nextInt(list.size()));
        }
    }



    private List<String> dealSongList(List<String> value, Jedis jedis, String pattern, Map<Integer, String> enumMap) {
        List<String> songList = new ArrayList<>(100);
        List<String> TempList = value.size() > 2 ? value.subList(0, 2) : value;
        for (String temp : TempList) {
            String key = enumMap.get(Integer.valueOf(temp));
            String newKey = String.format(pattern, key);
            String values = jedis.get(newKey);
            String[] split = values.split(";");
            List<String> songListTemp = Arrays.asList(split);
            songList.addAll(songListTemp.size() > 50 ? songList.subList(0, 50) : songListTemp);

        }
        return songList;
    }
    private List<String> dealArtistList(List<String> artistList, Jedis jedis, String pattern) {
        List<String> songList = new ArrayList<>(100);
        for (String artist : artistList) {
            String artistNewKey = String.format(pattern, artist);
            String artistSongs = jedis.get(artistNewKey);
            if (StringUtils.isEmpty(artistSongs)) {
                String[] split = artistSongs.split(";");
                List<String> tempList = Arrays.asList(split);
                songList.addAll(tempList.size() > 5 ? tempList.subList(0, 5) : tempList);
            }
        }
        return songList;
    }
}
