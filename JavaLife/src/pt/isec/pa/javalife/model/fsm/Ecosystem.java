package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.gameengine.IGameEngine;
import pt.isec.pa.javalife.gameengine.IGameEngineEvolve;
import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.UiElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Ecosystem implements Serializable, IGameEngineEvolve {
    @Serial
    private static final long serialVersionUID = 1L;
    private Set<IElement> elements;
    private int width, height;

    private Deque<IElement> newBornWaitingList = new ArrayDeque<>();

    private List<IElement> bounds = new ArrayList<>();

    public Ecosystem(int width, int height) {
        elements = new HashSet<>();
        this.width = width;
        this.height = height;
        initializeBoundaryElements();

    }

    public void resize(int newWidth, int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
        initializeBoundaryElements();
    }

    private void initializeBoundaryElements() {
        for (IElement e : bounds) {
            elements.remove(e);
        }
        bounds.clear();

        // Add rocks along the down edge
        bounds.add(new Rock(new Area(height + 1, 0, height, width + 1), this));

        // Add rocks along the right edge
        bounds.add(new Rock(new Area(height, width, 1, width + 1), this));

        // Add rocks along the top edge
        bounds.add(new Rock(new Area(1, 0, 0, (width + 1)), this));


        // Add rocks along the left edge
        bounds.add(new Rock(new Area(height, 0, 1, 1), this));


        for (IElement e : bounds) {
            elements.add(e);
        }
    }

    public IElement getCloserElement(IElement element, Element type) {
        double minDistance = Double.MAX_VALUE;
        IElement closerElement = null;

        for (IElement a : elements) {
            if (a.getType() == type && a != element) {
                double distance = Math.sqrt(Math.pow(
                        element.getArea().getCenterX() - a.getArea().getCenterX(), 2)
                        + Math.pow(element.getArea().getCenterY() - a.getArea().getCenterY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    closerElement = a;
                }
            }
        }

        return closerElement;
    }

    public Set<IElement> getElementsInArea(Area targetArea) {
        if (targetArea == null) {
            return null;
        }
        Set<IElement> elementsInArea = new HashSet<>();

        for (IElement e : elements) {
            Area eArea = e.getArea();
            if (isInsideArea(eArea, targetArea)) {
                elementsInArea.add(e);
            }
        }

        return elementsInArea;
    }

    /**
     * Tests if two areas overlap
     * @see <a href="https://stackoverflow.com/questions/23302698/java-check-if-two-rectangles-overlap-at-any-point">source</a>
     */
    private boolean isInsideArea(Area elementArea, Area targetArea) {
        double x1 = elementArea.esquerda();
        double x2 = elementArea.direita();
        double y1 = elementArea.baixo();
        double y2 = elementArea.cima();

        double x3 = targetArea.esquerda();
        double x4 = targetArea.direita();
        double y3 = targetArea.baixo();
        double y4 = targetArea.cima();

        return (x1 < x4) && (x3 < x2) && (y1 < y4) && (y3 < y2);
    }

    public boolean CheckElements(Area targetArea, Element type) {
        for (IElement a : elements) {
            if (a.getType() == type) {
                if (isInsideArea(a.getArea(), targetArea)) {
                    return true;
                }
            }
        }
        return false;
    }

    public IElement getWeakerFauna(Fauna animal) {
        double minStrength = Double.MAX_VALUE;
        IElement weakerAnimal = null;

        for (IElement a : elements) {
            if (a.getType() == Element.FAUNA && a != animal) {
                if (((Animal) a).getStrength() < minStrength) {
                    minStrength = ((Animal) a).getStrength();
                    weakerAnimal = a;
                }
            }
        }

        return weakerAnimal;
    }

    public boolean IsThereAnyFlora() {
        for (IElement a : elements) {
            if (a.getType() == Element.FLORA) {
                return true;
            }
        }
        return false;
    }

    public IElement getStrongerFauna(Fauna animal) {
        double maxStrength = Double.MIN_VALUE;
        IElement strongerAnimal = null;

        for (IElement a : elements) {
            if (a.getType() == Element.FAUNA && a != animal) {
                if (((Fauna) a).getStrength() > maxStrength) {
                    maxStrength = ((Fauna) a).getStrength();
                    strongerAnimal = a;
                }
            }
        }

        return strongerAnimal;
    }

    public void addChildAnimal() {
        Animal animal = new Animal(FindFreeZone(), this);

        System.out.println("I am adding a new animal");
        addToWaitingList(animal);

    }

    private Area FindFreeZone() {
        Random rand = new Random();
        int x = 0;
        int y = 0;
        boolean found = false;
        while (!found) {
            x = rand.nextInt(width - 1);
            y = rand.nextInt(height - 1);
            Area area = new Area(x, y, x + 1, y + 1);
            if (x > width)
                if (!CheckElements(area, Element.FAUNA)) {
                    System.out.println("I foudn the freezone: Area-> " + x + " " + y);
                    found = true;
                }
        }
        return new Area(x, y, x - 1, y + 1);
    }

    public void addElement(IElement element) {
        elements.add(element);
    }

    public void addElement(int x, int y, double strength, Element type) {
        elements.add(ElementFactory.CreateElement(x, y, strength, type, this));

    }

    public void removeElement(IElement element) {
        elements.remove(element);
    }

    public void removeElement(UiElement element) {
        for (IElement a : elements) {
            if (a == null || (a.getId() == element.getId() && a.getType() == element.getType())) {
                elements.remove(a);
                break;
            }
        }
    }

    public IElement findElement(int id, Element type) {
        for (IElement a : elements) {
            if (a.getId() == id && a.getType() == type) {
                return a;
            }
        }
        return null;
    }

    public boolean isBounds(IElement element) {
        for (IElement b : bounds) {
            if (b == element) {
                // == means it is the same reference to obj
                return true;
            }
        }
        return false;
    }

    public void editElement(IElement element, int id) {
      /*  for(IElement a:elements){
            if(a.getId()==id){
                elements.remove(a);
                elements.add(element);
            }
        }

       */
    }

    public double getDistanceBetweenElements(IElement element1, IElement element2) {
        double x1 = element1.getArea().getCenterX();
        double y1 = element1.getArea().getCenterY();
        double x2 = element2.getArea().getCenterX();
        double y2 = element2.getArea().getCenterY();

        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        for (Iterator<IElement> i = elements.iterator(); i.hasNext(); ) {
            IElement element = i.next();
            if (element.CheckIfDead()) {
                i.remove();
            }
            else
                try {
                    element.evolve();
                } catch (ConcurrentModificationException exception) {
                    System.out.println("Failed to update ecosistem");
                }
        }

        while (!newBornWaitingList.isEmpty()) {
            this.addElement(newBornWaitingList.pop());
        }
    }

    public Set<UiElement> getElements() {
        Set<UiElement> uiElements = new HashSet<>();
        for (IElement element : elements) {
            uiElements.add(ElementFactory.CreateUiElement(element));
        }

        return uiElements;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addToWaitingList(IElement newBorn) {
        this.newBornWaitingList.push(newBorn);
    }

    public void applySun() {
        elements.forEach(e -> {
            if (e instanceof Flora)
                e.applySun();
        });
    }
}
