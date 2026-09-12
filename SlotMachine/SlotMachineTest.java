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
 * @version 1.1
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
    // Ciclo 2 - Mini-ciclo 5: Intercambiar ruedas

    @Test
    public void testSwapWheelsExchangesSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);

        assertTrue(machine.ok());
        String[] config = machine.configuration();
        assertEquals("blue", config[0]);
        assertEquals("red", config[1]);
    }

    @Test
    public void testSwapWheelsFailsWithLessThanTwoWheels()
    {
        machine.addWheel(1);
        machine.swap(1, 1);
        assertFalse(machine.ok());
    }
    // Ciclo 2 - Mini-ciclo 6: Fijar y soltar una rueda

    @Test
    public void testHoldWheelPreventsSpin()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");

        machine.lock(1);
        assertTrue(machine.ok());

        machine.spin(1);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void testReleaseWheelAllowsSpinAgain()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");

        machine.lock(1);
        machine.unlock(1);
        assertTrue(machine.ok());

        machine.spin(1);
        assertTrue(machine.ok());
    }

    @Test
    public void testSpinAllSkipsHeldWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.lock(1);
        machine.spin();

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void testHoldWheelFailsWhenNoWheels()
    {
        machine.lock(1);
        assertFalse(machine.ok());
    }
    // Ciclo 2 - Mini-ciclo 7: Rotar una rueda n pasos

    @Test
    public void testRotateWheelAdvancesSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(1, "red");

        machine.spin(1, 1);

        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    @Test
    public void testRotateWheelWrapsAround()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(1, "green");

        machine.spin(1, 1);

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void testRotateWheelWithNegativeStepsWrapsBackward()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(1, "red");

        machine.spin(1, -1);

        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);
    }

    @Test
    public void testRotateWheelFailsWhenHeld()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");

        machine.lock(1);
        machine.spin(1, 1);

        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }
    // Ciclo 2 - Mini-ciclo 8: Dejar la máquina en una configuración dada

    @Test
    public void testSetConfigurationAppliesColors()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.spin(new String[]{"red", "blue"});

        assertTrue(machine.ok());
        String[] config = machine.configuration();
        assertEquals("red", config[0]);
        assertEquals("blue", config[1]);
    }

    @Test
    public void testSetConfigurationSkipsHeldWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "blue");

        machine.lock(1);
        machine.spin(new String[]{"red", "blue"});

        assertTrue(machine.ok());
        String[] config = machine.configuration();
        assertEquals("blue", config[0]); // no cambió, estaba fijada
        assertEquals("blue", config[1]);
    }

    @Test
    public void testSetConfigurationFailsWithWrongLength()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");

        machine.spin(new String[]{"red"});

        assertFalse(machine.ok());
    }

    @Test
    public void testSetConfigurationFailsWithUnknownColor()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.spin(new String[]{"purple"});

        assertFalse(machine.ok());
    }
}