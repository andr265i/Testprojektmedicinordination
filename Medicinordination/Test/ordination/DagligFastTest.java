package ordination;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligFastTest {

    private Patient patient;
    private Laegemiddel laegemiddel;
    private DagligFast dagligFast;

    private Dosis dosis1, dosis2, dosis3, dosis4;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Bo Hansen", 70.0);
        laegemiddel = new Laegemiddel("Acetylsalicylsyre", 4, 5, 6, "Styk");
        dagligFast = new DagligFast(LocalDate.of(2025, 3, 14), LocalDate.of(2025, 3, 14), patient, laegemiddel, 1, 0, 2, 1);


    }

    // TC2
    @Test
    void createDosis() {
        Dosis dosis = dagligFast.createDosis(LocalTime.of(10, 0), 2);
        assertNotNull(dosis);
        assertEquals(LocalTime.of(10, 0), dosis.getTid());
        assertEquals(2, dosis.getAntal());
    }

    // TC3
    @Test
    void dagligfast() {
        assertNotNull(dagligFast);
        assertTrue(patient.getOrdinationer().contains(dagligFast));
        assertEquals(laegemiddel, dagligFast.getLaegemiddel());
        assertEquals(LocalDate.of(2025, 3, 14), dagligFast.getStartDen());
        assertEquals(LocalDate.of(2025, 3, 14), dagligFast.getSlutDen());
        assertEquals(1,dagligFast.getDoser()[0].getAntal());
        assertEquals(0,dagligFast.getDoser()[1].getAntal());
        assertEquals(2,dagligFast.getDoser()[2].getAntal());
        assertEquals(1,dagligFast.getDoser()[3].getAntal());
        assertEquals(LocalTime.of(6,0),dagligFast.getDoser()[0].getTid());
        assertEquals(LocalTime.of(12,0),dagligFast.getDoser()[1].getTid());
        assertEquals(LocalTime.of(18,0),dagligFast.getDoser()[2].getTid());
        assertEquals(LocalTime.of(0,0),dagligFast.getDoser()[3].getTid());
    }

    // TC5
    @org.junit.jupiter.api.Test
    void samletDosis8dage() {
        DagligFast dagligFast1 = new DagligFast(LocalDate.of(2025,3,14),LocalDate.of(2025,3,21),patient,laegemiddel,1,2,0,1);
        assertEquals(32,dagligFast1.samletDosis());
    }

    // TC6
    @Test
    void samletDosis1dag() {
        DagligFast dagligFast2 = new DagligFast(LocalDate.of(2025,3,14),LocalDate.of(2025,3,14),patient,laegemiddel,1,2,0,1);
        assertEquals(4,dagligFast2.samletDosis());
    }

    // TC4
    @org.junit.jupiter.api.Test
    void doegnDosis() {
        assertEquals(4,dagligFast.doegnDosis());
    }


}