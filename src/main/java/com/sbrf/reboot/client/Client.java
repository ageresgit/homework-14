package com.sbrf.reboot.client;

import com.sbrf.reboot.account.Account;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Client {
    private String name;
    private int age;
    List<Account> accounts;
}
