package command;

import javafx.collections.ObservableList;
import model.Account;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;


public class RemoveTransactionsCommand implements Command {
    private List<Transaction> transactionsToRemove;
    private final Account account;

    public RemoveTransactionsCommand(ObservableList<Transaction> transactionsToRemove, Account account) {
        this.transactionsToRemove = new ArrayList<>(transactionsToRemove);
        this.account = account;
    }

    @Override
    public void execute() {
        for (Transaction transaction: transactionsToRemove)
            account.removeTransaction(transaction);
    }

    @Override
    public String getName() {
        return "Removed transaction: " + transactionsToRemove.toString();
    }

    @Override
    public void undo() {
        for(Transaction transaction: transactionsToRemove) {
            account.addTransaction(transaction);
        }
    }

    @Override
    public void redo() {
        for(Transaction transaction: transactionsToRemove) {
            account.removeTransaction(transaction);
        }
    }
}
