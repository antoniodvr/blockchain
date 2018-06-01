package io.antoniodvr.blockchain.business.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyTransaction extends Transaction {

    private String sender;
    private String receiver;
    private Double amount;

}
