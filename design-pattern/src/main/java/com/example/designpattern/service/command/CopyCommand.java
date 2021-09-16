package com.example.designpattern.service.command;

public class CopyCommand extends Command{

  Content content;

  public CopyCommand(Content content) {
    this.content = content;
  }
  @Override
  public void exec() {
    content.message = content.message + content.message;
  }

  @Override
  public void undo() {
    content.message = content.message.substring(0, content.message.length()/2);
  }
}
