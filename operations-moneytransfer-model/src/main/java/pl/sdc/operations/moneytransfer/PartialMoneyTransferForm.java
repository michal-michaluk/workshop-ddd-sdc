package pl.sdc.operations.moneytransfer;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PartialMoneyTransferForm {
    LocalDate transferDate;
    String creditAccountNumber;
    String debitAccountNumber;
}
