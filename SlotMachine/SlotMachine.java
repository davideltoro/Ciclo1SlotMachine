import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Simula una máquina tragamonedas con n ruedas y n símbolos posibles,
 * inspirada en el Problema I de la maratón ICPC 2025.
 * 
 * @author Juan Castellanos - Nicole Paez
 * @version 1.1
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private ArrayList<Symbol> symbols;
    private boolean ok;
    private boolean visible;

    private Rectangle body;
    private Rectangle base;
    private Rectangle arm;
    private Circle knob;

    /**
     * Crea una máquina tragamonedas vacía (sin ruedas ni símbolos)
     * junto con su representación visual.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<Symbol>();
        ok = true;
        visible = false;
        buildCasing();
    }

    /**
     * Construye y posiciona las figuras que forman la carcasa
     * de la máquina (cuerpo, base y palanca).
     */
    private void buildCasing()
    {
        body = new Rectangle();
        body.changeSize(150, 220);
        body.changeColor("blue");
        body.moveHorizontal(30 - 70);
        body.moveVertical(50 - 15);

        base = new Rectangle();
        base.changeSize(20, 160);
        base.changeColor("black");
        base.moveHorizontal(50 - 70);
        base.moveVertical(200 - 15);

        arm = new Rectangle();
        arm.changeSize(50, 10);
        arm.changeColor("black");
        arm.moveHorizontal(260 - 70);
        arm.moveVertical(60 - 15);

        knob = new Circle();
        knob.changeSize(30);
        knob.changeColor("red");
        knob.moveHorizontal(250 - 20);
        knob.moveVertical(30 - 15);
    }

    /**
     * Agrega una rueda nueva en la posición indicada (1-based).
     * @param pos posición donde insertar la rueda
     */
    public void addWheel(int pos)
    {
        int index = fixPosition(pos, wheels.size() + 1);
        Wheel w = new Wheel(index);
         if(index <= wheels.size()) {
            Wheel oldWheel = wheels.set(index - 1, w);
            oldWheel.makeInvisible();
        }
        else {
            wheels.add(w);
        }

        if(visible) {
            w.makeVisible();
        }
        ok = true;
    }

    /**
     * Elimina la rueda en la posición indicada (1-based).
     * @param pos posición de la rueda a eliminar
     */
    public void delWheel(int pos)
    {
        if(wheels.isEmpty()) {
            ok = false;
            showError("No hay ruedas para eliminar");
            return;
        }
        int index = fixPosition(pos, wheels.size());
        Wheel w = wheels.remove(index - 1);
        w.makeInvisible();
        ok = true;
    }

    /**
     * Agrega un símbolo nuevo del color dado en la posición indicada.
     * @param pos posición donde insertar el símbolo
     * @param color color del nuevo símbolo
     */
    public void addSymbol(int pos, String color)
    {
        for (int i = 0; i < symbols.size(); i++){
            if(symbols.get(i).getColor().equals(color)){
                ok=false;
                showError("El simbolo ya existe");
                return;
            }
        }
        int index = fixPosition(pos, symbols.size() + 1);
        Symbol s = new Symbol(color);
        symbols.add(index - 1, s);
        ok = true;
    }

    /**
     * Elimina el primer símbolo que tenga el color indicado.
     * @param symbol color del símbolo a eliminar
     */
    public void delSymbol(String symbol)
    {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getColor().equals(symbol)) {
                symbols.remove(i);
                ok = true;
                return;
            }
        }
        ok = false;
        showError("El simbolo a eliminar no existe");
    }

    /**
     * Hace visible la máquina completa: la carcasa y cada una de sus ruedas.
     */
    public void makeVisible()
    {
        body.makeVisible();
        base.makeVisible();
        arm.makeVisible();
        knob.makeVisible();
        for(Wheel w : wheels) {
            w.makeVisible();
        }
        visible = true;
        ok = true;
    }

    /**
     * Hace invisible la máquina completa: la carcasa y cada una de sus ruedas.
     */
    public void makeInvisible()
    {
        body.makeInvisible();
        base.makeInvisible();
        arm.makeInvisible();
        knob.makeInvisible();
        for(Wheel w : wheels) {
            w.makeInvisible();
        }
        visible = false;
        ok = true;
    }

    /**
     * Termina el simulador, ocultando toda su representación visual.
     */
    public void exit()
    {
        makeInvisible();
    }

    /**
     * @return true si la última operación se realizó con éxito
     */
    public boolean ok()
    {
        return ok;
    }

    /**
     * Ajusta una posición 1-based al rango válido [1, max].
     * @param pos posición solicitada
     * @param max valor máximo permitido
     * @return la posición corregida
     */
    private int fixPosition(int pos, int max)
    {
        if(pos < 1) {
            return 1;
        }
        if(pos > max) {
            return max;
        }
        return pos;
    }
        /**
     * Ubica el símbolo del color indicado en la rueda indicada.
     * @param wheel posición de la rueda (1-based)
     * @param symbol color del símbolo a colocar
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if(wheels.isEmpty() || symbols.isEmpty()) {
            ok = false;
            showError("No hay ruedas o símbolos disponibles.");
            return;
        }
        int sIndex = indexOfColor(symbol);
        if(sIndex == -1) {
            ok = false;
            showError("No existe un símbolo de color \"" + symbol + "\".");
            return;
        }
        int wIndex = fixPosition(wheel, wheels.size());
        Wheel w = wheels.get(wIndex - 1);
        w.setCurrentPosition(sIndex + 1);
        w.showSymbol(symbol);
        changesJackpot();
        ok = true;
    }
    
    /**
     * Gira la rueda indicada, dejándola en un símbolo aleatorio del catálogo.
     * @param wheel posición de la rueda a girar (1-based)
     */
    public void spin(int wheel)
    {
        if(wheels.isEmpty() || symbols.isEmpty()) {
            ok = false;
            showError("No hay ruedas o símbolos para girar.");
            return;
        }
        int wIndex = fixPosition(wheel, wheels.size());
        int randomIndex = (int)(Math.random() * symbols.size());
        Wheel w = wheels.get(wIndex - 1);
        w.setCurrentPosition(randomIndex + 1);
        w.showSymbol(symbols.get(randomIndex).getColor());
        changesJackpot();
        ok = true;
    }
        /**
     * Muestra un mensaje de error al usuario, únicamente si el
     * simulador está visible.
     * @param message el mensaje a mostrar
     */
    private void showError(String message)
    {
        if(visible) {
            JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }    
    /**
     * Gira todas las ruedas de la máquina.
     */
    public void spin()
    {
        for(int i = 1; i <= wheels.size(); i++) {
            spin(i);
        }
    }
    
    /**
     * Busca el índice del símbolo que tiene el color dado.
     * @param color el color buscado
     * @return el índice (0-based) o -1 si no existe
     */
    private int indexOfColor(String color)
    {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getColor().equals(color)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Busca saber los colores de los simbolos creados 
     * @return los colores de los simbolos
     */
    public String[] symbols(){
        String[] colors =new String [symbols.size()];
        for(int i=0;i< symbols.size();i++){
            colors[i]=symbols.get(i).getColor();
        }
        ok=true;
        return colors;
    }
    /**
     * Busca saber que simbolo esta en cada rueda 
     * @return positions los colores de los simbolos de cada rueda
     */
    public String [] configuration(){
        if(symbols.isEmpty()) {
        ok = false;
        showError("No hay símbolos disponibles.");
        return new String[0];
        }
        String[] positions = new String[wheels.size()];
        for (int i=0;i< wheels.size();i++){
            int position = wheels.get(i).getCurrentPosition();
            positions[i]= symbols.get(position-1).getColor();
        }
        ok = true;
        return positions;
    }
    
    /**
 * Cuenta cuántos colores distintos hay actualmente entre las ruedas.
 * @return el número de símbolos distintos visibles en la máquina
 */
    public int distinctSymbols(){
        String [] positions = configuration();
        ArrayList<String> diferent = new ArrayList<String>();
        for (int i=0;i< positions.length;i++){
            if (!diferent.contains(positions[i])){
                diferent.add(positions[i]);
            }
        }
        ok = true;
        return diferent.size();
        
    }

/**
 * Indica si la máquina está en configuración ganadora.
 * @return true si todas las ruedas muestran el mismo símbolo
 */
    public boolean isJackpot(){
        return distinctSymbols()==1;
    }

/**
 * Actualiza el color de la máquina según si hay jackpot, y
 * redibuja las ruedas para que queden por encima del cuerpo.
 */
    private void changesJackpot(){
        if(isJackpot()) {
            body.changeColor("green");
        } else {
            body.changeColor("blue");
        }
        if(visible) {
            for(Wheel w : wheels) {
                w.makeVisible();  
                }
            }
        }
}