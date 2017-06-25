package com.maersk.aoplatform.basicactor;

import java.io.Serializable;

/**
 * Created by g on 02/06/2017.
 */
public class AddMessage  implements Serializable {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AddMessage(int amount) {

        this.amount = amount;
    }
}
