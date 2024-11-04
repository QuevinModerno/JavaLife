package pt.isec.pa.javalife.model.data.elements;

public sealed interface IElementReproduce  permits Flora, Fauna{
    void reproduce();
}
