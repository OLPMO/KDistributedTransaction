package com.kdt.server.command;

import com.kdt.common.KTransactionMessage;
import com.kdt.server.util.TransactionInfoManager;

public interface CommandStrategy {
    void execute(KTransactionMessage transactionMessage, TransactionInfoManager transactionInfoManager);
}
