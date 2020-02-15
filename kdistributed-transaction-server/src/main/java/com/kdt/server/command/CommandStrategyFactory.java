package com.kdt.server.command;

import com.kdt.common.Command;

public class CommandStrategyFactory {
    public CommandStrategy getStrategy(Command command){
        CommandStrategy strategy = new DefaultStrategy();
        if (command == Command.COMMIT){
            strategy =  new CommitStrategy();
        }else if(command == Command.ROLLBACK){
            strategy = new RollbackStrategy();
        }else if(command == Command.REGISTE){
            strategy = new RegisteStrategy();
        }
        return strategy;
    }
}
