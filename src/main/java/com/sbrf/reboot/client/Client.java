package com.sbrf.reboot.client;

import com.sbrf.reboot.account.Account;
import lombok.*;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Client {
    private String name;
    private int age;
    List<Account> accounts;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Stream<Account> accountsAsStream() {
        return accounts.stream();
    }
}
