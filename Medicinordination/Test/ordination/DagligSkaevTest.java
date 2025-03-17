package ordination;


import controller.Controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligSkaevTest {
    private   Patient patient;
    private Laegemiddel laegemiddel;
    private DagligSkaev dagligSkaev;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Bo Hansen", 70);
       laegemiddel = new Laegemiddel("Acetylsalicylsyre", 4,
                5, 6, "Styk");

    }

    //TC7
    @org.junit.jupiter.api.Test
    void opretDosis() {
        Dosis dosis = null;
       LocalTime[] klokkeslet = {LocalTime.of(12,0)};
       double[] antalEnheder = {1};
        dagligSkaev = new DagligSkaev(LocalDate.of(2025,3,14),LocalDate.of(2025,3,14),
                patient,laegemiddel,klokkeslet,antalEnheder);
        for(int i = 0; i < dagligSkaev.getDoser().size();i++) {
            dosis = dagligSkaev.getDoser().get(i);
        }
        assertNotNull(dosis);
        assertEquals(LocalTime.of(12,0), dosis.getTid());
        assertEquals(1,dosis.getAntal());
    }

    //TC8
    @org.junit.jupiter.api.Test
    void DagligSkaev() {
        LocalTime[] klokkeslet = {LocalTime.of(6,30),LocalTime.of(12,0),LocalTime.of
                (14,10)};
        double[] antalEnheder = {4,2,1};
        dagligSkaev = new DagligSkaev(LocalDate.of(2025,3,14),LocalDate.of(2025,3,14),patient,laegemiddel,
                klokkeslet,antalEnheder);
        assertNotNull(dagligSkaev);
        assertEquals(LocalDate.of(2025,3,14),dagligSkaev.getStartDen());
        assertEquals(LocalDate.of(2025,3,14),dagligSkaev.getSlutDen());
        assertTrue(patient.getOrdinationer().contains(dagligSkaev));
        assertEquals(laegemiddel,dagligSkaev.getLaegemiddel());
        assertEquals(LocalTime.of(6,30),dagligSkaev.getDoser().get(0).getTid());
        assertEquals(LocalTime.of(12,0),dagligSkaev.getDoser().get(1).getTid());
        assertEquals(LocalTime.of(14,10),dagligSkaev.getDoser().get(2).getTid());
        assertEquals(4,dagligSkaev.getDoser().get(0).getAntal());
        assertEquals(2,dagligSkaev.getDoser().get(1).getAntal());
        assertEquals(1,dagligSkaev.getDoser().get(2).getAntal());

        }

    //TC9
    @org.junit.jupiter.api.Test
    void doegnDosis() {
        LocalTime[] klokkeslet = {LocalTime.of(6,30),LocalTime.of(12,0),LocalTime.of
                (14,0),LocalTime.of(22,00)};
        double[] antalEnheder = {1,2,0,1};
        dagligSkaev = new DagligSkaev(LocalDate.of(2025,3,14),LocalDate.of(2025,3,14),patient,laegemiddel,
                klokkeslet,antalEnheder);
        assertEquals(4,dagligSkaev.doegnDosis());
    }

    //TC 10
    @org.junit.jupiter.api.Test
    void samletDosis8Dage() {
        LocalTime[] klokkeslet = {LocalTime.of(6,30),LocalTime.of(12,0),LocalTime.of
                (14,0),LocalTime.of(22,00)};
        double[] antalEnheder = {1,2,0,1};
        dagligSkaev = new DagligSkaev(LocalDate.of(2025,3,14),LocalDate.of(2025,3,21),patient,laegemiddel,
                klokkeslet,antalEnheder);
        assertEquals(32,dagligSkaev.samletDosis());
    }

    //TC 11
    @org.junit.jupiter.api.Test
    void samletDosis1Dag() {
        LocalTime[] klokkeslet = {LocalTime.of(6,30),LocalTime.of(12,0),LocalTime.of
                (14,0),LocalTime.of(22,00)};
        double[] antalEnheder = {1,2,0,1};
        dagligSkaev = new DagligSkaev(LocalDate.of(2025,3,14),LocalDate.of(2025,3,14),patient,laegemiddel,
                klokkeslet,antalEnheder);
        assertEquals(4,dagligSkaev.samletDosis());
    }
}