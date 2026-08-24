import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Pruebas unitarias básicas para la clase SlotMachine.
 * 
 * @author Juan Castellanos - Nicole Paez
 * @version 1.0
 */
public class SlotMachineTest
{
    private SlotMachine machine;

    /**
     * Crea una máquina nueva antes de cada prueba (sin hacerla visible,
     * para no abrir ventanas durante las pruebas).
     */
    @Before
    public void setUp()
    {
        machine = new SlotMachine();
    }

    /**
     * Libera la referencia después de cada prueba.
     */
    @After
    public void tearDown()
    {
        machine = null;
    }

    @Test
    public void testAddWheel()
    {
        machine.addWheel(1);
        assertTrue(machine.ok());
    }

    @Test
    public void testDelWheelOnEmptyFails()
    {
        machine.delWheel(1);
        assertFalse(machine.ok());
    }

    @Test
    public void testAddSymbol()
    {
        machine.addSymbol(1, "red");
        assertTrue(machine.ok());
        assertEquals(1, machine.symbols().length);
    }

    @Test
    public void testAddDuplicateSymbolFails()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "red");
        assertFalse(machine.ok());
    }

    @Test
    public void testDelSymbolNotFoundFails()
    {
        machine.delSymbol("red");
        assertFalse(machine.ok());
    }

    @Test
    public void testIsJackpotWhenAllSame()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        assertTrue(machine.isJackpot());
    }

    @Test
    public void testIsNotJackpotWhenDifferent()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        assertFalse(machine.isJackpot());
        assertEquals(2, machine.distinctSymbols());
    }
}