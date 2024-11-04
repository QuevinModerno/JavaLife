package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.fsm.Ecosystem;

import java.io.Serializable;

public record Area(double cima, double esquerda,
                   double baixo, double direita) implements Serializable
{
    public double height(){
        return cima - baixo;
    }
    public double width(){
        return direita - esquerda;
    }

    public boolean isValid(){
        return (baixo <= cima) && (esquerda <= direita);
    }
    public boolean contains(int x, int y) {
        return y >= esquerda && y <= direita && x >= baixo && x <= cima;
    }

    public double getCenterX(){
        return (esquerda + direita) / 2;
    }
    public double getCenterY(){
        return (cima + baixo) / 2;
    }

    public Area CheckSourroundingArea(Ecosystem ecosystem)
    {
        //Check if any surrounding area is free

        double right = this.direita();
        double left = this.esquerda();
        double top =this.cima();
        double bottom = this.baixo();
        //RIGHT
        for(int i = 0; i < 4; i++)
        {
            if(i == Directions.RIGHT.ordinal()) {
                right += this.width();
                left += this.width();
            }
            else
            if(i == Directions.LEFT.ordinal()) {
                right -= this.width();
                left -= this.width();
            }
            else
            if(i == Directions.DOWN.ordinal()) {
                top -= this.height();
                bottom -= this.height();
            }
            else {
                top += this.height();
                bottom += this.height();
            }

            if(ecosystem.getElementsInArea(new Area(top, left, bottom, right)).isEmpty()
            && top > 0 &&  left > 0 && bottom < ecosystem.getHeight() && right < ecosystem.getWidth())
            {
                return new Area(top, left, bottom, right);
            }
        }

        return null;
    }
}
