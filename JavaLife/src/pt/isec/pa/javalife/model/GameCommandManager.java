package pt.isec.pa.javalife.model;

import pt.isec.pa.javalife.gameengine.GameEngine;
import pt.isec.pa.javalife.gameengine.GameEngineState;
import pt.isec.pa.javalife.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.commands.*;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public class GameCommandManager {

    private IGameEngine engine;
    private Ecosystem eco;

    private CommandManager cmdManager;

    public GameCommandManager(IGameEngine engine, Ecosystem eco) {
        this.engine = engine;
        this.eco = eco;
        this.cmdManager = new CommandManager();
    }

    public void addElement(double x, double y, double sizeX, double sizeY, double stength, Element type) {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.invokeCommand(new CommandAddElement(x, y, sizeX, sizeY, stength, type, eco));
    }

    public void removeElement(int id, Element type) {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.invokeCommand(new CommandRemoveElement(eco, id, type));
    }

    public void editElement(int id, double x, double y, double sizeX, double sizeY, double stength, Element type) {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.invokeCommand(new CommandEditElement(eco, id, type, x, y, sizeX, sizeY, stength));
    }

    public void resize(int newX, int newY) {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.invokeCommand(new CommandResize(eco, engine, newX, newY));
    }

    public void changeTimeStep(long newStep) {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.invokeCommand(new CommandSetTimeStep(eco, engine, newStep));
    }


    public void undo() {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.undo();
    }

    public void redo() {
        if (engine.getCurrentState() == GameEngineState.RUNNING) {
            return;
        }
        cmdManager.redo();
    }
}
