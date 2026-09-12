/**
 * Una rueda de la máquina tragamonedas. Guarda en qué posición
 * (índice dentro de la lista de símbolos de SlotMachine) está parada,
 * y se representa visualmente como una ventana con un círculo indicador
 * que cambia de color según el símbolo que muestra.
 * 
 * @author Juan David Castellanos - Nicole Paez
 * @version 1.2
 */
public class Wheel
{
    private int currentPosition;
    private Rectangle window;
    private Circle indicator;
    private boolean held;

    /**
     * Crea una rueda en la posición 1 por defecto, con su ventana
     * ubicada según el lugar que ocupa entre las demás ruedas.
     * @param slotIndex lugar (1-based) que ocupa esta rueda en la máquina
     */
    public Wheel(int slotIndex)
    {
        currentPosition = 1;
        int targetX = 45 + (slotIndex - 1) * 50;
        int targetY = 90;
        held = false;

        window = new Rectangle();
        window.changeSize(60, 40);
        window.changeColor("white");
        window.moveHorizontal(targetX - 70); // 70 es la x por defecto de Rectangle
        window.moveVertical(targetY - 15);   // 15 es la y por defecto de Rectangle

        indicator = new Circle();
        indicator.changeSize(24);
        indicator.changeColor("white");
        
        int circleX = targetX + (40 - 24) / 2;   // 40 = ancho real de la ventana
        int circleY = targetY + (60 - 24) / 2;   // 60 = alto real de la ventana
        indicator.moveHorizontal(circleX - 20);
        indicator.moveVertical(circleY - 15);
    }

    /**
     * @return la posición actual de la rueda
     */
    public int getCurrentPosition()
    {
        return currentPosition;
    }

    /**
     * Cambia la posición actual de la rueda.
     * @param pos la nueva posición
     */
    public void setCurrentPosition(int pos)
    {
        currentPosition = pos;
    }
    /**
     * @return true si la rueda está fijada (no debe girar)
     */
    public boolean isHeld()
    {
        return held;
    }

    /**
     * Fija o suelta la rueda.
     * @param held true para fijarla, false para soltarla
     */
    public void setHeld(boolean held)
    {
        this.held = held;
    }

    /**
     * Hace visible la ventana y el indicador de la rueda.
     */
    public void makeVisible()
    {
        window.makeVisible();
        indicator.makeVisible();
    }

    /**
     * Hace invisible la ventana y el indicador de la rueda.
     */
    public void makeInvisible()
    {
        window.makeInvisible();
        indicator.makeInvisible();
    }

    /**
     * Muestra visualmente el color del símbolo que le corresponde a esta
     * rueda, cambiando el color de su círculo indicador.
     * @param color el color del símbolo actual
     */
    public void showSymbol(String color)
    {
        indicator.changeColor(color);
    }
}