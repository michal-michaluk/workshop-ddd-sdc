package pl.sdc.operations.moneytransfer;

public interface NationalRegistraterBanks {
    BankInfo getInfoForAccountNumber(String debitAccountNumber);

    class BankInfo {
        public boolean sameBankAs(BankInfo other) {
            return false;
        }
    }
}
