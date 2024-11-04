package pt.isec.pa.javalife.model.data.elements;

public enum Element {
    INANIMADO, FLORA, FAUNA;

    public static Element GetElementByName(String str){
        switch (str.toUpperCase()){
            case "INANIMATE": return INANIMADO;
            case "FLORA": return FLORA;
            case "FAUNA": return FAUNA;
            default: return null;
        }
    }
}
