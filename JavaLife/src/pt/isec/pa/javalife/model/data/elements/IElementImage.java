package pt.isec.pa.javalife.model.data.elements;

public sealed interface IElementImage permits Flora {
    String getImagem(); // path da imagem
    void setImagem(String imagem);
}
