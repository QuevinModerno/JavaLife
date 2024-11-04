package pt.isec.pa.javalife.model.commands;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementFactory;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.IElementStrength;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public class CommandEditElement extends AbstractCommand {

    //ref to Element after
    private IElement addedElement = null;
    //ref to Element before
    private IElement removedElement = null;

    //param
    private int id;
    private Element type;

    public CommandEditElement(Ecosystem eco, int id, Element type, double x, double y, double sizeX, double sizeY, double stength) {
        super(eco);
        this.id = id;
        this.type = type;

        this.removedElement = eco.findElement(id, type);

        this.addedElement = ElementFactory.CloneElement(removedElement);

        addedElement.setArea(new Area(x, y, sizeX, sizeY));

        if (addedElement instanceof IElementStrength)
            ((IElementStrength) addedElement).setStrength(stength);
    }


    @Override
    public boolean execute() {
        if (removedElement == null || addedElement == null)
            return false;

        eco.removeElement(removedElement);
        eco.addElement(addedElement);
        return true;
    }

    @Override
    public boolean undo() {
        if (removedElement == null || addedElement == null)
            return false;

        eco.removeElement(addedElement);
        eco.addElement(removedElement);
        return true;
    }
}