/**
 * Un símbolo de la máquina tragamonedas, identificado por su color.
 * Cada símbolo dibuja su propio círculo en el canvas.
 * 
 * @author Juan Castellanos - Nicole Paez
 * @version 1.0
 */
public class Symbol
{
    private String color;
    private Circle figure;
    private int xPosition;
    private int yPosition;

    /**
     * Crea un símbolo con el color dado.
     * @param color el color del símbolo (nombre CSS, ej. "red")
     */
    public Symbol(String color)
    {
        this.color = color;
        figure = new Circle();
        figure.changeColor(color);
        xPosition = 20; // posición x por defecto de Circle
        yPosition = 15; // posición y por defecto de Circle
        
    }

    /**
     * Hace visible el símbolo en el canvas.
     */
    public void makeVisible()
    {
        figure.makeVisible();
    }

    /**
     * Hace invisible el símbolo en el canvas.
     */
    public void makeInvisible()
    {
        figure.makeInvisible();
    }

    /**
     * @return el color del símbolo
     */
    public String getColor()
    {
        return color;
    }
     /**
     * Mueve el círculo del símbolo a la posición absoluta (x, y).
     * Se usará para ubicarlo dentro de la ventana de una rueda.
     * @param x posición horizontal destino
     * @param y posición vertical destino
     */
    public void placeAt(int x, int y)
    {
        figure.moveHorizontal(x - xPosition);
        figure.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }
}