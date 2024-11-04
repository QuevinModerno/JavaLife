package pt.isec.pa.javalife.model.data.elements;

public sealed interface IElementStrength permits Inanimado, Flora, Fauna{
    double getStrength();
    void setStrength(double forca);
    void injectStrength();
}
