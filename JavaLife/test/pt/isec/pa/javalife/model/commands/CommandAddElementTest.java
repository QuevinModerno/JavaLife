package pt.isec.pa.javalife.model.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementFactory;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandAddElementTest {
    ICommand cmd;

    Ecosystem eco;

//    IElement testElement;

    @BeforeEach
    void setUp() {
        eco = new Ecosystem(10, 10);

//        testElement = ElementFactory.CreateElement(2, 2, 2, 2, 50, Element.FAUNA, this.eco);

//        eco.addElement(testElement);

        cmd = new CommandAddElement(this.eco, 1, 1, 1, 1, 90, Element.FAUNA);
    }

    @Test
    void execute() {
        //arrange
//        Area a = testElement.getArea();

        //act
        cmd.execute();

        //assert
        assertTrue(eco.getElements().stream().filter(x-> x.getType()== Element.FAUNA).count() > 0);//element

    }

    @Test
    void undoAndRedo() {
        cmd.execute();
        cmd.undo();

        assertTrue(eco.getElements().stream().filter(x-> x.getType()== Element.FAUNA).count() == 0);//element

        cmd.execute();

        assertTrue(eco.getElements().stream().filter(x-> x.getType()== Element.FAUNA).count() > 0);//element
    }
}