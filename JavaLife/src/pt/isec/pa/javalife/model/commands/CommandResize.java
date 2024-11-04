package pt.isec.pa.javalife.model.commands;

import pt.isec.pa.javalife.gameengine.GameEngineState;
import pt.isec.pa.javalife.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public class CommandResize extends AbstractCommand {
    private IGameEngine engine;
    private int newX;
    private int newY;
    private int oldX;
    private int oldY;

    public CommandResize(Ecosystem eco, IGameEngine engine, int newX, int newY) {
        super(eco);
        this.engine = engine;
        this.newX = newX;
        this.newY = newY;
        this.oldY = eco.getHeight();
        this.oldX = eco.getWidth();
    }

    @Override
    public boolean execute() {
    if(engine == null || engine.getCurrentState() == GameEngineState.RUNNING)
        return false;

    eco.resize(newX, newY);

    return true;
    }

    @Override
    public boolean undo() {
        if(engine == null || engine.getCurrentState() == GameEngineState.RUNNING)
            return false;

        eco.resize(oldX, oldY);

        return true;
    }
}
