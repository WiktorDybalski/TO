package command;

public class AddTransationCommand implements Command{

    public AddTransactionCommand(Transaction transactionToAdd, Account account) {
        this.transactionToAdd = transactionToAdd;
        this.account = account;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return "New transaction: " + transactionToAdd.toString();
    }
}
