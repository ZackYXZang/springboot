package com.example.designpattern.service.command;

public class InsertCommand extends Command{

  Content content;
  String insertContent = "handsome";
  public InsertCommand(Content content) {
    this.content = content;
  }

  @Override
  public void exec() {
    content.message = content.message + insertContent;
  }

  @Override
  public void undo() {
    content.message = content.message.substring(0, content.message.length() - insertContent.length());
  }
}
