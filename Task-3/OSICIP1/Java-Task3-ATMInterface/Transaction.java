public class Transaction {
    private String type;
    private double amount;
    private int accountId;
    private int recipientAccountId;

    public Transaction(String type, double amount, int accountId, int recipientAccountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.recipientAccountId = recipientAccountId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    @Override
    public String toString() {
        if ("Transfer".equals(type)) {
            return type + " : ₹" + amount + " to Account " + recipientAccountId;
        }
        return type + " : ₹" + amount;
    }
}
