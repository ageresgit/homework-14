package com.sbrf.reboot.account;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Currency {
    RUB("Российский рубль"),
    USD("Доллар США"),
    EUR("Евро");

    private String name;

    private Currency(String name) {
        this.name = name;
    }
}
