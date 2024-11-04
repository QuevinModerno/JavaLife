package pt.isec.pa.javalife.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker {

    private IManager manager;
    private Deque<IMemento> history;
    private Deque<IMemento> redoHist;

    public CareTaker(IManager manager) {
        this.manager = manager;
        history = new ArrayDeque<>();
        redoHist = new ArrayDeque<>();
    }

    public void save() {
        redoHist.clear();
        history.push(manager.save());
    }

    public void undo() {
        if (history.isEmpty())
            return;
        redoHist.push(manager.save());
        manager.restore(history.pop());
    }

    public void redo() {
        if (redoHist.isEmpty())
            return;
        history.push(manager.save());
        manager.restore(redoHist.pop());
    }

    public void reset() {
        history.clear();
        redoHist.clear();
    }

    public boolean hasUndo() {
        return !history.isEmpty();
    }

    public boolean hasRedo() {
        return !redoHist.isEmpty();
    }
}
