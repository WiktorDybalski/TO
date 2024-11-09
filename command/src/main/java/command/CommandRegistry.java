package command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CommandRegistry {

	private ObservableList<Command> commandUndoStack = FXCollections
			.observableArrayList();

	private List<Command> commandRedoStack = FXCollections
			.observableArrayList();

	public void executeCommand(Command command) {
		command.execute();
		commandUndoStack.add(command);
		commandRedoStack.clear();
	}

	public void redo() {
		if (!commandRedoStack.isEmpty()) {
			Command lastDone = commandRedoStack.getLast();
			commandRedoStack.removeLast();
			lastDone.redo();
			commandUndoStack.add(lastDone);
		}
	}

	public void undo() {
		if (!commandUndoStack.isEmpty()) {
			Command lastDone = commandUndoStack.getLast();
			commandUndoStack.removeLast();
			lastDone.undo();
			commandRedoStack.add(lastDone);
		}
	}

	public ObservableList<Command> getCommandStack() {
		return commandUndoStack;
	}
}
