package com.sbrf.reboot.reports;

import com.sbrf.reboot.account.Account;
import com.sbrf.reboot.account.Currency;
import com.sbrf.reboot.client.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
@Setter
public class MainReport {

    private Currency currency;
    private int minAge;
    private int maxAge;
    LocalDate minDate;
    LocalDate maxDate;

    public BigDecimal getTotalsWithCompletableFuture(Stream<Client> inData) {
       //return BigDecimal.ZERO;
        return inData.filter(client -> (client.getAge() >= minAge))
                .filter(client->(client.getAge() <= maxAge))
                .map(Client::getAccounts)
                .flatMap(Collection::stream)
                .filter(acc->currency.equals(acc.getCurrency()))
                .filter(acc->minDate.isBefore(acc.getDateCreate()))
                .filter(acc->maxDate.isAfter(acc.getDateCreate()))
                .map(Account::getBalance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
        ;
    }
}
