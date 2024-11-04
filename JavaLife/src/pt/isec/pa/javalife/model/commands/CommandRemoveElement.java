package pt.isec.pa.javalife.model.commands;

import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public class CommandRemoveElement extends AbstractCommand {

    //ref to removed Element
    private IElement removedElement;

    //param
    private int id;
    private Element type;

    public CommandRemoveElement(Ecosystem eco, int id, Element type) {
        super(eco);
        this.id = id;
        this.type = type;
        this.removedElement = eco.findElement(id, type);
    }

    @Override
    public boolean execute() {
        if (removedElement == null || eco.isBounds(removedElement))
            return false;

        eco.removeElement(removedElement);
        return true;
    }

    @Override
    public boolean undo() {
        if (removedElement == null)
            return false;
        eco.addElement(removedElement);
        return true;
    }
}
