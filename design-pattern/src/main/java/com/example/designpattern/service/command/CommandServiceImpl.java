package com.example.designpattern.service.command;

import org.springframework.stereotype.Service;

/**
 * 命令模式
 */
@Service
public class CommandServiceImpl {

  public void command() {
    Content content = new Content();

    Command insertCommand = new InsertCommand(content);
    insertCommand.exec();
    insertCommand.undo();

    Command copyCommand = new CopyCommand(content);
    copyCommand.exec();
    copyCommand.undo();
    System.out.println(content);

  }
}
