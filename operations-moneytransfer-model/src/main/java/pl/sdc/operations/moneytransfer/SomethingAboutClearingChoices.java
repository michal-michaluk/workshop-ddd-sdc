package pl.sdc.operations.moneytransfer;

import lombok.AllArgsConstructor;
import pl.sdc.bank.BankId;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SomethingAboutClearingChoices {

    private BankId id;
    // possible Clearing Choices and priorities are configurable by bank
    private PossibleClearingChoices possible;
    private List<ClearingChoicesFilter> filters;

    private NationalRegistraterBanks register;

    public AvailableClearingChoices availableClearingChoices(PartialMoneyTransferForm form) {
        // money transfers inside single bank* doesn't require clearing
        if (register.getInfoForAccountNumber(form.getCreditAccountNumber())
                .sameBankAs(register.getInfoForAccountNumber(form.getDebitAccountNumber()))) {
            return AvailableClearingChoices.noClearingRequired();
        }

        return possible.filterOptions(filters);
    }


    interface ClearingChoicesFilter {
        boolean isAvailable(PartialMoneyTransferForm form, ClearingChoices choices);
    }

    @AllArgsConstructor
    public static class FutureTransferExcludeFilter implements ClearingChoicesFilter {
        private final Clock clock;
        private final ClearingChoices onlyOption;

        @Override
        public boolean isAvailable(PartialMoneyTransferForm form, ClearingChoices choices) {
            LocalDate currentDate = LocalDate.now(clock);
            return choices.equals(onlyOption) || form.getTransferDate().equals(currentDate);
        }
    }

    @AllArgsConstructor
    public static class TimeSpanExcludeFilter implements ClearingChoicesFilter {
        private final LocalTime from;
        private final LocalTime to;
        private final ClearingChoices excluded;
        private final Clock clock;

        @Override
        public boolean isAvailable(PartialMoneyTransferForm form, ClearingChoices choices) {
            // time between 13:30 - 19:30 exclude "Same day transfer" Clearing Choice
            // time span 13:30 - 19:30 can be configured by bank

            LocalTime currentTime = LocalTime.now(clock);
            return choices.equals(excluded)
                    && from.isBefore(currentTime) && currentTime.isBefore(to);
        }
    }
}
