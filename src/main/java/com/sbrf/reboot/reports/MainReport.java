package com.sbrf.reboot.reports;

import com.sbrf.reboot.account.Account;
import com.sbrf.reboot.account.Currency;
import com.sbrf.reboot.client.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Getter
@Setter
public class MainReport {

    private Currency currency;
    private int minAge;
    private int maxAge;
    LocalDate minDate;
    LocalDate maxDate;

    public BigDecimal getTotalsSimple(Stream<Client> inData) {
        return inData.filter(client -> (client.getAge() >= minAge))
                .filter(client->(client.getAge() <= maxAge))
                .flatMap(Client::accountsAsStream)
                .filter(acc->currency.equals(acc.getCurrency()))
                .filter(acc->minDate.isBefore(acc.getDateCreate()))
                .filter(acc->maxDate.isAfter(acc.getDateCreate()))
                .map(Account::getBalance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getTotalsWithCompletableFuture(Stream<Client> inData) {
        Stream<Client> filteredData = inData.filter(client->(client.getAge() >= minAge && client.getAge() <= maxAge));
        return filteredData.map(
                        client->CompletableFuture.supplyAsync(()->client.accountsAsStream()
                            .filter(acc->currency.equals(acc.getCurrency()))
                            .filter(acc->minDate.isBefore(acc.getDateCreate()))
                            .filter(acc->maxDate.isAfter(acc.getDateCreate()))
                            .map(Account::getBalance)
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO)
                        ).join()
        ).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public BigDecimal getTotalsWithReact(Stream<Client> inData) {
        Flux<Client> fluxData = Flux.fromStream(inData);
        Flux<Mono<BigDecimal>> fluxMono =
        fluxData.filter(client->(client.getAge() >= minAge && client.getAge() <= maxAge))
                .map(Client::accountsAsStream)
                .map(streamAcc->Flux.fromStream(streamAcc).filter(acc->currency.equals(acc.getCurrency())).filter(acc->minDate.isBefore(acc.getDateCreate()))
                        .filter(acc->maxDate.isAfter(acc.getDateCreate()))
                        .map(Account::getBalance)
                        .reduce(BigDecimal::add)
                );
        return fluxMono.blockLast().block();
    }
}
