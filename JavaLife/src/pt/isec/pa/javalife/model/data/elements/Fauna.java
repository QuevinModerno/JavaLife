package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.Directions;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import java.util.*;

public abstract sealed class Fauna extends ElementStandard implements IElementStrength, IElementReproduce, IMove permits Animal{
    static int id = 0;
    private int Speed;
    private double Strength;
    private ClientContext context;
    private double loseStrength;
    private IElement ChaseReference;
    private boolean sunEffectActive;
    private int sunEffectDuration;

    static int defaultStrength = 50;
    static int defaultSpeed = 1;
    static double defaultLoseStrength  = 0.5;

    public Fauna(Fauna original) {
        super(original);
        this.Strength = original.getStrength();
        this.Speed = original.Speed;
        this.loseStrength = original.loseStrength;
    }

    public static boolean setDefaultStrength(int newDefaultStrength) {
        if(newDefaultStrength <= 0 && newDefaultStrength > 100 ) {
            return false;
        }
        Fauna.defaultStrength = newDefaultStrength;
        return true;
    }
    public static boolean setDefaultSpeed(int newDefaultSpeed) {
        if(newDefaultSpeed <= 0 && newDefaultSpeed > 100 ) {
            return false;
        }
        Fauna.defaultSpeed = newDefaultSpeed;
        return true;
    }
    public static boolean setDefaultLoseStrength(double newDefaultLoseStrength) {
        if(newDefaultLoseStrength <= 0 && newDefaultLoseStrength > 100 ) {
            return false;
        }
        Fauna.defaultLoseStrength = newDefaultLoseStrength;
        return true;
    }

    public Fauna (Area area, Ecosystem ecosystem)
    {
        super(id++, Element.FAUNA, area, ecosystem);
        this.Speed = 1;
        this.Strength = defaultStrength;
        this.setArea(area);
        this.context = new ClientContext(ecosystem, this);
        this.loseStrength = 0.5;
        this.ChaseReference = null;
    }
    public Fauna (Area area,double strength, Ecosystem ecosystem)
    {
        super(id++, Element.FAUNA, area, ecosystem);
        this.Speed = 1;
        this.Strength = strength;
        this.setArea(area);
        this.context = new ClientContext(ecosystem, this);
        this.loseStrength = 0.5;
        this.ChaseReference = null;
    }


    @Override
    public double getStrength() {
        return this.Strength;
    }

    @Override
    public void setStrength(double strength) {
        if(strength >= 0 && strength <= 100){
            this.Strength = strength;
        }

    }
    public void setReference(IElement element){
        this.ChaseReference = element;
    }
    public IElement getReference(){
        return this.ChaseReference;
    }
    public ClientStates getState() {
        return this.context.getState();
    }
    @Override
    public void evolve() {
        this.Strength -= this.loseStrength;
        context.evolve();
    }

    @Override
    public void reproduce() {
        this.ecosystem.addChildAnimal();
    }

    @Override
    public void move() {

        //preciso de fazer um sistema que me permita mover o animal
        //para a direção do elemento que ele está a perseguir
        //ou para uma posicao aleatória
        //Atualizando uma nova area para o animal
        //e atualizando a sua posição

        //se tiver um animal a perseguir
        //se posicao nao tiver um elemnto com type= rock
        //vai para a direção do elemento que ele está a perseguir
        //se não tiver um animal a perseguir ou se tiver um elemento com type=rock
        //vai para uma posicao aleatória disponivel(que não tenha um elemento com type=rock)
        //se não tiver nenhuma posicao disponivel
        //não se move

        //Funcoes necessarias:

        if(this.ChaseReference == null){
            MovesRandomly();
            //se não tiver um animal a perseguir
            //vai para uma posicao aleatória disponivel(que não tenha um elemento com type=rock)
            //se não tiver nenhuma posicao disponivel não se move
        }
        else{
            if(CheckifPositionAvailable(this.ChaseReference.getArea()))
            {
                Directions dir = FindElementDirection(this.ChaseReference.getArea());
                moveToDirection(dir);
            }
            else
            {
                MovesRandomly();
            }
            //se tiver um animal a perseguir
            //se posicao nao tiver um elemnto com type= rock
            //vai para a direção do elemento que ele está a perseguir
            //se não tiver um animal a perseguir ou se tiver um elemento com type=rock
            //vai para uma posicao aleatória disponivel(que não tenha um elemento com type=rock)
            //se não tiver nenhuma posicao disponivel
            //não se move
            //Funcoes necessarias:
            //getDirectionToElement()
            //moveToDirection()
            //getRandomPosition()
            //getAvailablePosition()
            //moveToPosition()
        }
    }

    @Override
    public boolean CheckIfDead(){
        return this.Strength <= 0;
    }

    private void MovesRandomly() {
        List<Directions> directions = new ArrayList<>();
        Collections.addAll(directions, Directions.values());
        Random rand = new Random();

        while (!directions.isEmpty()) {
            int randIndex = rand.nextInt(directions.size());
            Directions randomDirection = directions.get(randIndex);

            if (moveToDirection(randomDirection)) {
                // Move was successful
                break;
            } else {
                // Remove the unsuccessful direction from the list
                directions.remove(randIndex);
            }
        }
    }


    //Moves animal to a certain direction.
    // Returns true if the animal was able to move to the direction, false otherwise
    private boolean moveToDirection(Directions direction){
        Area newAnimalArea = switch (direction) {
            case UP -> new Area(this.getArea().cima() - 1, this.getArea().esquerda(), this.getArea().baixo() - 1, this.getArea().direita());
            case DOWN ->
                    new Area(this.getArea().cima() + 1, this.getArea().esquerda(), this.getArea().baixo() + 1, this.getArea().direita());
            case LEFT ->
                    new Area(this.getArea().cima(), this.getArea().esquerda() - 1, this.getArea().baixo(), this.getArea().direita() - 1);
            case RIGHT ->
                    new Area(this.getArea().cima(), this.getArea().esquerda() + 1, this.getArea().baixo(), this.getArea().direita() + 1);
        };


        if(!CheckifPositionAvailable(newAnimalArea))
        {
            return false;
        }
        else
        {
            this.setArea(newAnimalArea);
            return true;
        }
    }

    public boolean CheckifPositionAvailable(Area area)
    {
        Set<IElement> elements = ecosystem.getElementsInArea(area);

        if(elements == null || elements.isEmpty() )
        {
            return true;
        }
        for(IElement element : elements){
            if(element.getType() == Element.INANIMADO){
                return false;
            }
        }


        return true;
        /*
        if(!elements.isEmpty()){
            return elements.stream().noneMatch(e -> e.getType() == Element.ROCK);
        }
        else{
            return true;
        }

         */
    }
    private Directions FindElementDirection(Area ChaseArea)
    {
        double difX = this.getArea().getCenterX() - ChaseArea.getCenterX();
        double difY = this.getArea().getCenterY() - ChaseArea.getCenterY();


        // Compare absolute values of differences to determine the primary direction
        if (Math.abs(difX) > Math.abs(difY)) {
            // Horizontal movement
            if (difX > 0) {
                return Directions.LEFT;
            } else {
                return Directions.RIGHT;
            }
        } else {
            // Vertical movement
            if (difY > 0) {
                return Directions.UP;
            } else {
                return Directions.DOWN;
            }
        }
    }

    @Override
    public void applyHerbicide() {

    }

    @Override
    public void injectStrength() {
        this.Strength += 50;
    }

    @Override
    public void applySun() {
        sunEffectActive = true;
        sunEffectDuration = 10;
    }
}
