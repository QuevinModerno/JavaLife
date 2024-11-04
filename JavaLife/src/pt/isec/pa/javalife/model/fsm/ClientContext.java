package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Element;

import java.io.Serializable;

public class ClientContext implements Serializable {

    private Ecosystem ecosystem;
    private transient IClientState state;
    protected ClientStates lastState;
    private Fauna fauna;

    public ClientContext(Ecosystem _manageEcosystem, Fauna Fauna) {
        this.ecosystem = _manageEcosystem;
        this.fauna = Fauna;
        state = ClientStates.MOVING.createState(this, this.ecosystem, this.fauna);
    }

    void changeState(IClientState newState) {
        this.lastState = this.getState();
        System.out.println("Changing state from " + this.state.getState() + " to " + newState.getState());
        this.state = newState;
    }

    public void evolve() {
        if (this.state == null)//file load safety
            this.state = ClientStates.MOVING.createState(this, this.ecosystem, this.fauna);
        this.state.evolve();
    }

    public void Move() {
        this.fauna.move();
    }

    public boolean onMessageReceived(Object message) {
        return state.onMessageReceived(message);
    }

    public boolean selOpt(Element opt, String string) {
        return state.selOpt(opt, string);
    }

    public ClientStates getLastState() {
        return lastState;
    }

    public ClientStates getState() {
        return state.getState();
    }

}