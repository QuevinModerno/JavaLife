package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.elements.Element;

import java.io.Serializable;

public interface IClientState extends Serializable {

    boolean selOpt(Element opt, String string);
    boolean onMessageReceived(Object message);
    ClientStates getState();
    void evolve();
}