package pt.isec.pa.javalife.model;

public interface IMemento {

    default Object getSnapshot() {
        return null;
    }
}
