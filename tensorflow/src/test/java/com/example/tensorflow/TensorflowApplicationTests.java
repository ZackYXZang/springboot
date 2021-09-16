package com.example.tensorflow;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.StringUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

@SpringBootTest
class TensorflowApplicationTests {

  @Test
  void contextLoads() {
    testTensorFlow();



  }

  public void testTensorFlow() {
    try (Graph graph = new Graph()) {
      //导入图

      byte[] graphBytes = IOUtils.toByteArray(new
          FileInputStream("/Users/yuxiangzang/Downloads/1630935559/saved_model.pb"));

      graph.importGraphDef(graphBytes);

      //根据图建立Session
      try(Session session = new Session(graph)){
        //相当于TensorFlow Python中的sess.run(z,feed_dict = {'x': 10.0})
        float z = session.runner()
            .feed("x", Tensor.create(10.0f))
            .fetch("z").run().get(0).floatValue();
        System.out.println("z = " + z);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}
