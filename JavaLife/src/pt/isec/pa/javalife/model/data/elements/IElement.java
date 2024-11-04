package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.Area;

import java.io.Serializable;

public sealed interface IElement extends Serializable permits ElementStandard {
    int getId(); // retorna o identificador

    Element getType(); // retorna o tipo

    Area getArea(); // retorna a área ocupada

    void setArea(Area area);

    void evolve(); // evolui o elemento

    boolean CheckIfDead(); // verifica se o elemento está morto
    void applyHerbicide();
    void applySun();
}
