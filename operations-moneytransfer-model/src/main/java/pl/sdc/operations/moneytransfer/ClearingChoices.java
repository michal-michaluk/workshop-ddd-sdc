package pl.sdc.operations.moneytransfer;

import lombok.Value;

@Value
public class ClearingChoices {
    String code;
    String name;
    Object coreBankValue;

    public boolean isCoreBankValueDefined() {
        return coreBankValue != null;
    }
}
