package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.data.elements.Animal;
import pt.isec.pa.javalife.model.data.elements.Grass;
import pt.isec.pa.javalife.model.data.elements.Rock;
import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.UiElement;

public class ElementFactory {

    public static IElement CreateElement(int x, int y, double stength, Element type, Ecosystem eco) {
        return CreateElement(x, y, 1, 1, stength, type, eco);
    }

    public static IElement CreateElement(double x, double y, double stength, Element type, Ecosystem eco) {
        return CreateElement(x, y, 1, 1, stength, type, eco);
    }

    public static IElement CreateElement(double x, double y, double sizeX, double sizeY, double stength, Element type, Ecosystem eco) {
        Area area = new Area(x + sizeX, y, x , y + sizeY);
        IElement elem = switch (type) {
            case FAUNA -> new Animal(area, eco);
            case FLORA -> new Grass(area, eco);
            case INANIMADO -> new Rock(area, eco);
            case null, default -> null;
        };

        if(elem instanceof IElementStrength ){
           ((IElementStrength) elem).setStrength(stength);
        }

        return elem;
    }

    public static IElement CloneElement(IElement original) {
        if (original == null) return null;

        IElement newElem = switch (original.getType()) {
            case FAUNA -> new Animal((Animal) original);
            case FLORA -> new Grass((Grass) original);
            case INANIMADO -> new Rock((Rock) original);
            case null, default -> null;
        };
        return newElem;
    }

    public static UiElement CreateUiElement(IElement baseElement) {

        UiElement uiElem = new UiElement(baseElement.getId(),
                baseElement instanceof IElementStrength
                        ? ((IElementStrength) baseElement).getStrength()
                        : 0,
                baseElement.getArea(),
                baseElement.getType());

        return uiElem;
    }

}
