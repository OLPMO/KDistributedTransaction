package com.kdt.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KTransactionMessage {
    private Command command;
    private String groupID;
    private String transactionID;
    private boolean isFinalTx;
}
