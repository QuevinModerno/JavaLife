package pt.isec.pa.javalife.model.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.javalife.gameengine.GameEngine;
import pt.isec.pa.javalife.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import static org.junit.jupiter.api.Assertions.*;

class CommandSetTimeStepTest {
    ICommand cmd;
    private Ecosystem eco;
    private IGameEngine engine;

    static long before = 1000;
    static long after = 500;

    @BeforeEach
    void setUp() {
        eco = new Ecosystem(10, 10);

        engine = new GameEngine();
        engine.start(before);

        cmd = new CommandSetTimeStep(eco, engine, after);
    }

    @Test
    void execute() {
        cmd.execute();

        assertTrue(engine.getInterval() == after);

    }

    @Test
    void undo() {
        cmd.execute();

        cmd.undo();

        assertTrue(engine.getInterval() == before);
    }
}