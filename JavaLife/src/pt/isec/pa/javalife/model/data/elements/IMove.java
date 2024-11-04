package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.elements.Animal;

import java.io.Serializable;

public sealed interface IMove extends Serializable permits Fauna {
    void move();
}
