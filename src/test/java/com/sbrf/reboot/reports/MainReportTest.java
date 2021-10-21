package com.sbrf.reboot.reports;

import com.sbrf.reboot.account.Account;
import com.sbrf.reboot.account.Currency;
import com.sbrf.reboot.client.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainReportTest  {
    public List<Client> clients;

    @BeforeEach
    public void setUp() {
        Client client25 = new Client("Ivan", 25, Arrays.asList(
            new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now()),
            new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now().minusDays(10)),
            new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now().minusDays(100)))
        );

        Client client27 = new Client("John", 27, Arrays.asList(
            new Account(BigDecimal.TEN, Currency.EUR, LocalDate.now().minusDays(10)),
            new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now().minusDays(10)),
            new Account(BigDecimal.TEN, Currency.USD, LocalDate.now().minusDays(10)))
        );

        Client client18 = new Client("Ian", 18, Arrays.asList(
           new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now().minusDays(10)),
           new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now().minusDays(10)),
           new Account(BigDecimal.TEN, Currency.RUB, LocalDate.now().minusDays(10)))
        );

        clients = Arrays.asList(client18, client25, client27);
    }

    @Test
    void getTotalsSimpleTest() {
        MainReport report = new MainReport(Currency.RUB, 20, 30, LocalDate.now().minusDays(20), LocalDate.now().minusDays(5));
        assertEquals(BigDecimal.valueOf(20), report.getTotalsWithCompletableFuture(clients.stream()));
    }

    @Test
    void getTotalsWithCompletableFutureTest() {
        MainReport report = new MainReport(Currency.RUB, 20, 30, LocalDate.now().minusDays(20), LocalDate.now().minusDays(5));
        assertEquals(BigDecimal.valueOf(20), report.getTotalsWithCompletableFuture(clients.stream()));
    }

    @Test
    void getTotalsWithReact() {
        MainReport report = new MainReport(Currency.RUB, 20, 30, LocalDate.now().minusDays(20), LocalDate.now().minusDays(5));
        assertEquals(BigDecimal.valueOf(20), report.getTotalsWithReact(clients.stream()));
    }
}
