package com.example.designpattern.service.strategy;

import org.springframework.stereotype.Service;

/**
 * 策略模式
 * @param <T>
 */
@Service
public class StrategyServiceImpl<T> {


  /**
   * 定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的变化不会影响使用算法的客户。
   * 策略模式属于对象行为模式，它通过对算法进行封装，把使用算法的责任和算法的实现分割开来，并委派给不同的对象对这些算法进行管理。
   * @param animals
   * @param comparator
   */
  public void sort(T[] animals, AnimalComparator<T> comparator) {
    System.out.println("策略模式 start");
    for (int i = 0 ; i < animals.length; i++) {
      int minPos = i;

      for (int j = i + 1; j < animals.length; j++) {
        minPos = comparator.compare(animals[j], animals[minPos]) == -1 ? j : minPos;
      }
      swap(animals, i, minPos);
    }
    System.out.println("策略模式 end");
  }

  private void swap(T[] animals, int i, int minPos) {
    T temp = animals[i];
    animals[i] = animals[minPos];
    animals[minPos] = temp;
  }
}
