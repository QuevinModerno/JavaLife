package pt.isec.pa.javalife.model;

public interface IManager {

    IMemento save();
    void restore(IMemento memento);
}
