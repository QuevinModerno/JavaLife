package pt.isec.pa.javalife.model.commands;

import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.model.data.ElementFactory;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public class CommandAddElement extends AbstractCommand {

    //ref to added Element
    private IElement addedElement;

    //params
    private double x;
    private double y;
    private double sizeX;
    private double sizeY;
    private double stength;
    private Element type;


    protected CommandAddElement(Ecosystem eco, double x, double y, double sizeX, double sizeY, double stength, Element type) {
        super(eco);
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.stength = stength;
        this.type = type;
        addedElement = null;
    }

    public CommandAddElement(double x, double y, double sizeX, double sizeY, double stength, Element type, Ecosystem eco) {
        this(eco, x, y, sizeX, sizeY, stength, type);
    }

    @Override
    public boolean execute() {
        if (addedElement == null)
            this.addedElement = ElementFactory.CreateElement(
                    this.x, this.y, this.sizeX, this.sizeY, this.stength, this.type, this.eco);

        if (eco.getElementsInArea(addedElement.getArea()).stream().filter(x -> x.getType() == Element.INANIMADO).count() != 0)
            return false;
        eco.addElement(addedElement);
        return true;
    }

    @Override
    public boolean undo() {

        eco.removeElement(addedElement);

        return false;
    }
}
