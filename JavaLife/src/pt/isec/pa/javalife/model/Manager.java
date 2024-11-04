package pt.isec.pa.javalife.model;

import pt.isec.pa.javalife.gameengine.GameEngine;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Manager implements IManager {
    private PropertyChangeSupport pcs;
    private Ecosystem ecosystem;
    boolean isRunning = false;
    private GameEngine engine;
    public static final String PROP_ELEMENTS = "elements";
    private CareTaker careTaker;

    UiElement selectedElement;

    private GameCommandManager cmdManager;

    /**
     * Constructor for the Manager class
     * Initializes the ecosystem and game engine
     * @param width - the width of the ecosystem
     * @param height - the height of the ecosystem
     */
    public Manager(int width, int height) {
        ecosystem = new Ecosystem(width, height);

        pcs = new PropertyChangeSupport(this);
        engine = new GameEngine();
        engine.registerClient(ecosystem);
        engine.registerClient((g, t) -> this.evolve(t));

        cmdManager = new GameCommandManager(engine, ecosystem);
        careTaker = new CareTaker(this);
    }

    /**
     * Adds an observable to the collection that will be informed upon changes to the data model
     *
     * @param listener - lambda expression called when the data model updates
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     *  This evolve exists to notify the UI when there are any changes
     *
     * @param currentTime - the current time of the game engine
     */
    public void evolve(long currentTime) {
        pcs.firePropertyChange(null, null, null);
    }
   /* public void selectOption(Element options, String string) {
        boolean res = fsm.selOpt(options,string);
        pcs.firePropertyChange(null,null,res);
    }
    public ClientStates getState() {
        return fsm.getState();
    }

    */
    /**
     * Method to provide the View with dummy elements (containing the minimum information that needs to be represented)
     *
     * @return a set of UiElement representing the elements in the ecosystem
     */
    public Set<UiElement> getElements() {
        return ecosystem.getElements();
    }

    /**
     * Method to provide the View with dummy elements (containing the minimum information that needs to be represented)
     * filters out inanimate objects
     *
     * @return a set of UiElement representing the elements in the ecosystem excluding inanimate objects
     */
    public Set<UiElement> getListElements() {
        Set<UiElement> listElements = new HashSet<>();
        Set<UiElement> allElements = ecosystem.getElements();
        for (UiElement e : allElements) {
            if (e.getType() != Element.INANIMADO) {
                listElements.add(e);
            }
        }

        return listElements;
    }
    /**
     * Changes the ecosystem to paused mode
     */
    public void Pause() {
        isRunning = false;

    }
    /**
     * Provides the current width of the ecosystem
     *
     * @return width as int
     */
    public int getRows() {
        return ecosystem.getWidth();
    }
    /**
     * Provides the current height of the ecosystem
     *
     * @return height as int
     */
    public int getColumns() {
        return ecosystem.getHeight();
    }
    /**
     * Prepares the ecosystem without starting the engine
     */
    public void Setup() {
        this.isRunning = true;
        pcs.firePropertyChange(null, null, null);
    }
    /**
     * Starts the engine and simulation
     */
    public void start() {
        engine.start(1000);
        this.isRunning = true;
        pcs.firePropertyChange(null, null, null);
    }
    /**
     * Checks whether the simulation is ongoing
     *
     * @return boolean indicating whether the simulation is running
     */
    public boolean isRunning() {
        return isRunning;
    }
    /**
     * Stores the binary information of the ecosystem as a file
     *
     * @param file - the destination file
     * @return boolean indicating whether the file was written to
     */
    public boolean save(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this.ecosystem);
        } catch (Exception e) {
            System.err.println("Error writing Ecosystem to file");
            return false;
        }
        return true;

    }
    /**
     * Resets the ecosystem
     */
    public void clearAll() {
        ecosystem.resize(ecosystem.getWidth(), ecosystem.getWidth());
        cmdManager = new GameCommandManager(engine, ecosystem);
    }
    /**
     *
     * Reads the binary information of the ecosystem from a file
     *
     * @param file - the file with data
     * @return boolean indicating whether the file was read successfully
     */
    public boolean load(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            engine.unregisterClient(ecosystem);
            ecosystem = (Ecosystem) ois.readObject();
            engine.registerClient(ecosystem);
            cmdManager = new GameCommandManager(engine, ecosystem);
        } catch (Exception e) {
            System.err.println("Error loading Ecosystem");
            return false;
        }

        pcs.firePropertyChange(null, null, null);
        return true;
    }
    /**
     * Changes the engine to paused mode
     */
    public void pause() {
        this.engine.pause();
    }
    /**
     * Changes the ecosystem to running mode
     */
    public void resume() {
        this.engine.resume();
    }

    /**
     * Changes the ecosystem to paused mode and the engine to stopped
     */
    public void stop() {
        this.engine.stop();
        this.isRunning = false;
    }
    /**
     * restarts the ecosystem with new dimensions
     *
     * @param width - the new width of the ecosystem
     * @param height - the new height of the ecosystem
     */
    public void reset(int width, int height) {
        this.engine.stop();
        this.ecosystem = new Ecosystem(width, height);
        this.isRunning = false;
        pcs.firePropertyChange(null, null, null);
        engine.registerClient(ecosystem);
    }

    /**
     * Adds an element to the ecosystem at x and y coordinates with specified strength and type
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param strength - the strength of the element
     * @param type - the type of element
     */
    private void addElement(int x, int y, double strength, Element type) {
        ecosystem.addElement(x, y, strength, type);
    }

    /**
     * Adds an element to the ecosystem at x and y coordinates with specified strength and type
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param strength - the strength of the element
     * @param type - the type of element
     */
    public void addElement(int x, int y, double strength, String type) {
        Element elm = Element.GetElementByName(type);
        if (elm != null)
            this.addElement(x, y, strength, elm);

    }

    /**
     * Uses the commandManager to adds an element to the ecosystem
     *
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param width - the width of the element
     * @param height - the height of the element
     * @param strength - the strength of the element
     * @param type - the type of element
     */
    private void addElement(int x, int y, int width, int height, double strength, Element type) {
        cmdManager.addElement(x, y, width, height, strength, type);
    }

    /**
     * Adds an element to the ecosystem at x and y coordinates with specified strength and type
     *
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param width - the width of the element
     * @param height - the height of the element
     * @param strength - the strength of the element
     * @param type - the type of element
     */
    public void addElement(int x, int y, int width, int height, double strength, String type) {
        Element elm = Element.GetElementByName(type);
        if (elm != null)
            this.addElement(x, y, width, height, strength, elm);
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Uses the commandManager to edit an element in the ecosystem
     *
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param width - the width of the element
     * @param height - the height of the element
     * @param strength - the strength of the element
     * @param type - the type of element
     */
    private void editElement(int id, int x, int y, int width, int height, double strength, Element type) {
        cmdManager.editElement(id,  x, y, width, height, strength, type);
    }
    /**
     * Edits the element corresponding to selectedElement
     *
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param width - the width of the element
     * @param height - the height of the element
     * @param strength - the strength of the element
     * @param type - the type of element
     */
    public void editElement(int x, int y, int width, int height, double strength, String type) {
        Element elm = Element.GetElementByName(type);
        if (elm != null && selectedElement != null && selectedElement.getType() == elm)
            this.editElement(selectedElement.getId() , x, y, width, height, strength, elm);
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Removes an element from the ecosystem
     *
     * @param element - the UiElement to be removed
     */
    public void removeElement(UiElement element) {
//        ecosystem.removeElement(element);
        cmdManager.removeElement(element.getId(), element.getType());
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Sets the dimension of the ecosystem
     *
     * @param size - the new size of the ecosystem
     */
    public void setEcosystemDimension(int size) {
        engine.unregisterClient(ecosystem);
        ecosystem = new Ecosystem(size, size);
        engine.registerClient(ecosystem);
        cmdManager = new GameCommandManager(engine, ecosystem);
    }

    /**
     * Gets the dimension of the ecosystem
     *
     * @return the dimension of the ecosystem
     */
    public int getEcosystemDimension() {
        return ecosystem.getWidth();
    }

    /**
     * Gets the step interval of the engine
     *
     * @return the step interval of the engine
     */
    public long getStep() {
        return engine.getInterval();
    }

    /**
     * Sets the step interval of the engine
     *
     * @param step - the step interval to be set
     */
    public void setStep(long step) {
        if (step > 0)
            cmdManager.changeTimeStep(step);
    }

    /**
     * Undoes the last command
     */
    public void undo() {
        cmdManager.undo();
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Redoes the last undone command
     */
    public void redo() {
        cmdManager.redo();
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Saves the current state of the ecosystem
     *
     * @return a memento representing the current state
     */
    @Override
    public IMemento save() {
        return new Memento(ecosystem);
    }

    /**
     * Restores the ecosystem to a previous state
     *
     * @param memento - the memento containing the state to be restored
     */
    @Override
    public void restore(IMemento memento) {
        Object obj = memento.getSnapshot();
        if (obj instanceof Ecosystem e)
            ecosystem = e;
    }

    /**
     * Saves a snapshot of the current ecosystem state
     */
    public void saveSnapshot() {
        careTaker.save();
    }

    /**
     * Restores the ecosystem to a previously saved snapshot
     */
    public void restoreSnapshot() {
        careTaker.undo();
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Applies sunlight to the ecosystem
     */
    public void applySun() {
//        for (UiElement element : ecosystem.getElements()) {
//            element.applySun();
//        }
        ecosystem.applySun();
        pcs.firePropertyChange(null, null, null);
    }

    /**
     * Applies herbicide to the selected flora element
     */
    public void applyHerbicide() {
        if (selectedElement.getType() == Element.FLORA) {
            ecosystem.findElement(selectedElement.getId(), selectedElement.getType()).applyHerbicide();
            pcs.firePropertyChange(null, null, null);
        }
    }

    /**
     * Injects strength to the selected fauna element
     */
    public void injectStrength() {
        if (selectedElement.getType() == Element.FAUNA) {
            ((Fauna)ecosystem.findElement(selectedElement.getId(), selectedElement.getType())).injectStrength();
            pcs.firePropertyChange(null, null, null);
        }
    }

    /**
     * Selects an element in the ecosystem
     *
     * @param selectedItem - the UiElement to be selected
     */
    public void selectElement(UiElement selectedItem) {
        selectedElement = selectedItem;
    }

    private void deleteElement(int id, Element type) {
        cmdManager.removeElement(id, type);

    }
    public void deleteElement() {
        if(selectedElement == null) return;

        deleteElement( selectedElement.getId(), selectedElement.getType());

        selectedElement = null;

        pcs.firePropertyChange(null, null, null);
    }
}
