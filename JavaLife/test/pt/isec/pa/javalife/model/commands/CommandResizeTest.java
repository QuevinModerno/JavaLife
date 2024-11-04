package pt.isec.pa.javalife.model.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.javalife.gameengine.GameEngine;
import pt.isec.pa.javalife.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import static org.junit.jupiter.api.Assertions.*;

class CommandResizeTest {
    ICommand cmd;
    private Ecosystem eco;
    private IGameEngine engine;

    static int widthBefore = 10;
    static int widthAfter = 15;
    static int heightBefore = 20;
    static int heightAfter = 25;

    @BeforeEach
    void setUp() {
        eco = new Ecosystem(widthBefore, heightBefore);

        engine = new GameEngine();


        cmd = new CommandResize(eco, engine, widthAfter, heightAfter);
    }


    @Test
    void execute() {
        cmd.execute();

        assertTrue(eco.getWidth() == widthAfter && eco.getHeight() == heightAfter);
    }

    @Test
    void undo() {
        cmd.execute();
        cmd.undo();
        assertTrue(eco.getWidth() == widthBefore && eco.getHeight() == heightBefore);
    }
}