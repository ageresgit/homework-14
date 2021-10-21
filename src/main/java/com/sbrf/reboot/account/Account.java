package com.sbrf.reboot.account;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Account {
    private BigDecimal balance;
    private Currency currency;
    private LocalDate dateCreate;
}
