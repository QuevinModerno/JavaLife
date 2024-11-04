package pt.isec.pa.javalife.model.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementFactory;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import static org.junit.jupiter.api.Assertions.*;

class CommandEditElementTest {

    ICommand cmd;

    Ecosystem eco;

    IElement testElement;

    @BeforeEach
    void setUp() {
        eco = new Ecosystem(10, 10);

        testElement = ElementFactory.CreateElement(2, 2, 2, 2, 50, Element.FAUNA, this.eco);

        eco.addElement(testElement);

        cmd = new CommandEditElement(this.eco, testElement.getId(), testElement.getType(), 1, 1, 1, 1, 90);
    }

    @Test
    void execute() {
        //arrange
        Area a = testElement.getArea();

        //act
        cmd.execute();

        //assert
        assertFalse(eco.findElement(testElement.getId(), testElement.getType()) == null);//element exists
        assertFalse(eco.findElement(testElement.getId(), testElement.getType()).getArea().cima() ==
                testElement.getArea().cima());//element is not the added element
    }

    @Test
    void undoAndRedo() {
        cmd.undo();

        assertFalse(eco.findElement(testElement.getId(), testElement.getType()) == null);//element exists

        assertTrue(eco.findElement(testElement.getId(), testElement.getType()).getArea().cima() ==
                testElement.getArea().cima());//element is not the added element

        cmd.execute();
        //assert
        assertFalse(eco.findElement(testElement.getId(), testElement.getType()) == null);//element exists
        assertFalse(eco.findElement(testElement.getId(), testElement.getType()).getArea().cima() ==
                testElement.getArea().cima());//element is not the added element
    }
}