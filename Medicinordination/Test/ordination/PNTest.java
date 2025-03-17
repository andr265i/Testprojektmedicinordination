package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PNTest {
    private PN pn;
    private Patient patient;
    private Laegemiddel laegemiddel;


    @BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Bo Hansen", 70.0);
        laegemiddel = new Laegemiddel("Acetylsalicylsyre", 4, 5, 6, "Styk");
        pn = new PN(LocalDate.of(2025,3,13), LocalDate.of(2025,3,15),patient, laegemiddel, 2);
    }

    //TC12
    @Test
    public void PN() {
        assertNotNull(pn);
        assertEquals(2, pn.getAntalEnheder());
        assertTrue(patient.getOrdinationer().contains(pn));
        assertEquals(laegemiddel,pn.getLaegemiddel());
        assertEquals(LocalDate.of(2025,3,13),pn.getStartDen());
        assertEquals(LocalDate.of(2025,3,15),pn.getSlutDen());
    }

    //TC13
    @Test
    void givDosis13() {
        assertTrue(pn.givDosis(LocalDate.of(2025,3,13)));

        ArrayList<LocalDate> expected = new ArrayList<>();
        expected.add(LocalDate.of(2025,3,13));
        assertEquals(expected, pn.getDatoListe());
    }

    //TC14
    @Test
    void givDosis14() {
        assertTrue(pn.givDosis(LocalDate.of(2025,3,14)));

        ArrayList<LocalDate> expected = new ArrayList<>();
        expected.add(LocalDate.of(2025,3,14));
        assertEquals(expected, pn.getDatoListe());
    }

    //TC15
    @Test
    void givDosis15() {
        assertTrue(pn.givDosis(LocalDate.of(2025,3,15)));

        ArrayList<LocalDate> expected = new ArrayList<>();
        expected.add(LocalDate.of(2025,3,15));
        assertEquals(expected, pn.getDatoListe());
    }

    //TC16
    @Test
    void givDosis16() {
        assertFalse(pn.givDosis(LocalDate.of(2025,3,16)));

        ArrayList<LocalDate> expected = new ArrayList<>();
        assertEquals(expected, pn.getDatoListe());
    }

    //TC18
    @Test
    void givDosis12() {
        assertFalse(pn.givDosis(LocalDate.of(2025,3,12)));

        ArrayList<LocalDate> expected = new ArrayList<>();
        assertEquals(expected, pn.getDatoListe());
    }

    //TC19
    @Test
    void antalDage1() {
        pn.givDosis(LocalDate.of(2025,3,13));

        assertEquals(1, pn.antalDage());

    }
    //TC20
    @Test
    void antalDage2() {
        pn.givDosis(LocalDate.of(2025,3,13));
        pn.givDosis(LocalDate.of(2025,3,15));

        assertEquals(3, pn.antalDage());
    }

    //TC21
    @Test
    void antalDage0() {
        assertEquals(0, pn.antalDage());
    }

    //TC22
    @Test
    void doegnDosis1() {
        pn.givDosis(LocalDate.of(2025,3,13));

        assertEquals(2, pn.doegnDosis());
    }

    //TC23
    @Test
    void doegnDosis2() {
        pn.givDosis(LocalDate.of(2025,3,13));
        pn.givDosis(LocalDate.of(2025,3,15));

        assertEquals(1.33, pn.doegnDosis(),0.01);
    }

    //TC24
    @Test
    void doegnDosis0() {
        assertEquals(0, pn.doegnDosis());
    }

    //TC25
    @Test
    void samletDosis1() {
        assertEquals(0, pn.samletDosis());
    }

    //TC26
    @Test
    void samletDosis3() {
        pn.givDosis(LocalDate.of(2025,3,13));
        pn.givDosis(LocalDate.of(2025,3,14));

        assertEquals(4, pn.samletDosis());
    }


}