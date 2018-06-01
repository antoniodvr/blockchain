package io.antoniodvr.blockchain.business.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChainValidity {

    private boolean valid;

    private ChainValidity(boolean valid) {
        this.valid = valid;
    }

    public static ChainValidity valid() {
        return new ChainValidity(true);
    }
    public static ChainValidity notValid() {
        return new ChainValidity(false);
    }

}
