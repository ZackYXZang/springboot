package com.example.springboot;

/**
 * @author yuxiangzang
 * @create 2020-08-03-7:15 下午
 * @desc
 **/
public class LowState extends AbstractState{
  public LowState(ScoreContext h)
  {
    hj=h;
    stateName="不及格";
    score=0;
  }
  public LowState(AbstractState state)
  {
    hj=state.hj;
    stateName="不及格";
    score=state.score;
  }
  public void checkState()
  {
    if(score>=90)
    {
      hj.setState(new HighState(this));
    }
    else if(score>=60)
    {
      hj.setState(new MiddleState(this));
    }
  }
}
