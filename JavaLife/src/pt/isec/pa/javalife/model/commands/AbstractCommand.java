package pt.isec.pa.javalife.model.commands;

import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public abstract class AbstractCommand implements ICommand {
    //    protected Manager gameManager;
    protected Ecosystem eco;

    protected AbstractCommand(Ecosystem eco) {
        this.eco = eco;
    }

}
