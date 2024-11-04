package pt.isec.pa.javalife.model.commands;

import pt.isec.pa.javalife.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public class CommandSetTimeStep extends AbstractCommand {
    private long newStep;
    private long oldStep;
    private  IGameEngine engine;

    public CommandSetTimeStep(Ecosystem eco, IGameEngine engine, long newStep) {
        super(eco);
        this.newStep = newStep;
        this.engine = engine;
        this.oldStep = this.engine.getInterval();
    }

    @Override
    public boolean execute() {
        engine.setInterval(newStep);
        return true;
    }

    @Override
    public boolean undo() {
        engine.setInterval(oldStep);
        return true;
    }
}
