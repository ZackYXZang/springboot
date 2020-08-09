package com.example.springboot;

/**
 * @author yuxiangzang
 * @create 2020-08-03-7:14 下午
 * @desc
 **/
public class ScoreContext {
  private AbstractState state;
  ScoreContext()
  {
    state=new LowState(this);
  }
  public void setState(AbstractState state)
  {
    this.state=state;
  }
  public AbstractState getState()
  {
    return state;
  }
  public void add(int score)
  {
    state.addScore(score);
  }
}
