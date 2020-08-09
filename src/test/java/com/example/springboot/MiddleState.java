package com.example.springboot;

/**
 * @author yuxiangzang
 * @create 2020-08-03-7:16 下午
 * @desc
 **/
public class MiddleState extends AbstractState{
  public MiddleState(AbstractState state)
  {
    hj=state.hj;
    stateName="中等";
    score=state.score;
  }
  public void checkState()
  {
    if(score<60)
    {
      hj.setState(new LowState(this));
    }
    else if(score>=90)
    {
      hj.setState(new HighState(this));
    }
  }
}
