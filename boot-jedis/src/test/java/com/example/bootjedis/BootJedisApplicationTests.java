package com.example.bootjedis;

import com.alibaba.fastjson.JSONObject;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.User;
import com.example.bootjedis.utils.LocalDateUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class BootJedisApplicationTests {

  @Autowired
  private JedisPool jedisPool;

  @Autowired
  private UserService userService;

  @Autowired
  private RestTemplate restTemplate;

  public String getLiveResult() {
    String sign = getSign();
    String sessionListUrl = "http://api.changbalive.com/v2/middleplatform/hourrank/gethourranktop" + sign;
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
      //  执行HTTP请求
      ResponseEntity<String> response = restTemplate
          .exchange(sessionListUrl, HttpMethod.GET, requestEntity, String.class);
      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  //生成sign
  public String getSign() {
    String secret = "a%f9b4_xq3.13a0/a4fsx.1af94ajf1*";
    Map<String, String> paramBase = new TreeMap<>(new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    long time = LocalDateUtils.getUnixSecondTime(LocalDateTime.now());
    paramBase.put("appid", "middle_platform");
    paramBase.put("time", String.valueOf(time));
    StringBuilder sb = new StringBuilder();
    for (Map.Entry entry : paramBase.entrySet()) {
      if (!entry.getKey().equals("sign")) { //拼装参数,排除sign
        if (!StringUtils.isEmpty(entry.getKey()) && !StringUtils.isEmpty(entry.getValue())) {
          try {
            sb.append("&").append(entry.getKey()).append("=").append(URLDecoder.decode((String) entry.getValue(), "UTF-8"));
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
        }
      }
    }
    String sign = SHA(sb.toString().substring(1) + secret, "SHA-512");
    String result = "?" + sb.toString().substring(1) + "&sign=" + sign.substring(0, 20) + sign.substring(40, 60);
    return result;
  }



  private String SHA(final String strText, final String strType)
  {
    // 返回值
    String strResult = null;

    // 是否是有效字符串
    if (strText != null && strText.length() > 0)
    {
      try
      {
        // SHA 加密开始
        // 创建加密对象 并傳入加密類型
        MessageDigest messageDigest = MessageDigest.getInstance(strType);
        // 传入要加密的字符串
        messageDigest.update(strText.getBytes());
        // 得到 byte 類型结果
        byte byteBuffer[] = messageDigest.digest();

        // 將 byte 轉換爲 string
        StringBuffer strHexString = new StringBuffer();
        // 遍歷 byte buffer
        for (int i = 0; i < byteBuffer.length; i++)
        {
          String hex = Integer.toHexString(0xff & byteBuffer[i]);
          if (hex.length() == 1)
          {
            strHexString.append('0');
          }
          strHexString.append(hex);
        }
        // 得到返回結果
        strResult = strHexString.toString();
      }
      catch (NoSuchAlgorithmException e)
      {
        e.printStackTrace();
      }
    }

    return strResult;
  }


  @Test
  void contextLoads() {

    test();
  }
  private static Integer count = 0;
  final BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(5);//容量为5的阻塞队列

  public void test()  {
    BootJedisApplicationTests t = new BootJedisApplicationTests();
    new Thread(t.new Producer()).start();
    new Thread(t.new Consumer()).start();
    new Thread(t.new Consumer()).start();
    new Thread(t.new Producer()).start();
  }
  class Producer implements Runnable {
    @Override
    public void run() {
      for (int i = 0; i < 5; i++) {
        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        try {
          bq.put(1);
          count++;
          System.out.println(Thread.currentThread().getName() + "produce:: " + count);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
  class Consumer implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 5; i++) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        try {
          bq.take();
          count--;
          System.out.println(Thread.currentThread().getName()+ "consume:: " + count);
        } catch (Exception e) {
          // TODO: handle exception
          e.printStackTrace();
        }
      }
    }
  }

  public List<Object> mixData(List<Object> list1,List<Object> list2,int p1,int p2) {
    if(list1 ==  null || list1.size() <= 0) {
      return list2;
    }
    if(list2 == null || list2.size() <= 0) {
      return list1;
    }
    int sumCount = list1.size() + list2.size();
    int p = p1+p2;
    List<Object> list = new ArrayList<>();
    int m = 0;
    int n = 0;

    int count = 0;
    while(count < sumCount) {
      if(n == list2.size()) {
        while(m < list1.size()) {
          count++;
          list.add(list1.get(m));
          m++;
        }
      }
      if(m == list1.size()) {
        while(n < list2.size()) {
          count++;
          list.add(list2.get(n));
          n++;
        }
      }
      if(count%p == 0) {
        while(m < list1.size()) {
          count++;
          list.add(list1.get(m));
          m++;
          if(m%p1 == 0) break;
        }

      } else {
        while(n < list2.size()) {
          count++;
          list.add(list2.get(n));
          n++;
          if(n%p2 == 0) break;
        }
      }
    }

    return list;
  }

  public void testJedis() {

    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    String string = JSONObject.toJSONString(list);
    System.out.println(string);
  }

  public List<Integer> testList(List<Integer> list) {

    return list.subList(0,3);
  }

}
