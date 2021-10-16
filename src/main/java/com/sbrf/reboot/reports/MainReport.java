package com.sbrf.reboot.reports;

import com.sbrf.reboot.account.Currency;
import com.sbrf.reboot.client.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class MainReport {
    public static BigDecimal getTotalsWithCompletableFuture(Stream<Client> inData, Currency curr, int minAge, int maxAge, LocalDate minDate, LocalDate maxDate) {
        return BigDecimal.ZERO;
    }
}
