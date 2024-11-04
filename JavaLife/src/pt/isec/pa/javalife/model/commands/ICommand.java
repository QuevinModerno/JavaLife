package pt.isec.pa.javalife.model.commands;

public interface ICommand {
    boolean execute();
    boolean undo();
}

