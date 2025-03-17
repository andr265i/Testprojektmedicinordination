package controller;

import ordination.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Patient patient;
    private Laegemiddel laegemiddel;

    @BeforeEach
    void setUp() {
        Controller.setStorage(new Storage());
        patient = new Patient("12346-7890","Bo Hansen",70);
        laegemiddel = new Laegemiddel("Acetylsalicylsyre",4,5,6, "Styk");
    }

    //TC27
    @Test
    void opretPNOrdination() {
        PN pn = Controller.opretPNOrdination(LocalDate.of(2025,3,14),LocalDate.of(2025,3,17),patient,laegemiddel,2);

        assertNotNull(pn);
    }

    //TC28
    @Test
    void opretPNOrdination2() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
            Controller.opretPNOrdination(LocalDate.of(2025,3,17),LocalDate.of(2025,3,14),patient,laegemiddel,2));

        assertEquals(exception1.getMessage(), "startDato skal være før slutDato");
    }

    // TC29
    @Test
    void opretDagligFastOrdination() {
        DagligFast dagligFast = Controller.opretDagligFastOrdination(LocalDate.of(2025,3,14),LocalDate.of(2025,3,17),patient,laegemiddel,2,0,1,1);
        assertNotNull(dagligFast);
    }

    // TC30
    @Test
    void opretDaligFastOrdination2(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.opretDagligFastOrdination(LocalDate.of(2025,3,17),LocalDate.of(2025,3,14),patient,laegemiddel,2,0,1,1));

        assertEquals(exception.getMessage(),"startDato skal være før slutDato");
    }

    //TC31
    @Test
    void opretDagligSkaevOrdination() {
        LocalTime[] klokkeslet = {LocalTime.of(10,30),LocalTime.of(14,0), LocalTime.of
                (22,0)};
        double[] antalEnheder = {2,1,4};
        DagligSkaev dagligSkaev = Controller.opretDagligSkaevOrdination(LocalDate.of(2025,3,14),
                LocalDate.of(2025,3,17),patient,laegemiddel,klokkeslet,antalEnheder);
        assertNotNull(dagligSkaev);
    }

    //TC32
    @Test
    void opretDagligSkaevOrdination2() {
        LocalTime[] klokkeslet = {LocalTime.of(10,30),LocalTime.of(14,0), LocalTime.of
                (22,0)};
        double[] antalEnheder = {2,1,4};
        Exception exception =assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligSkaevOrdination(LocalDate.of(2025,3,17),LocalDate.of(2025,3,14)
                    ,patient,laegemiddel,klokkeslet,antalEnheder);
        });
        assertEquals(exception.getMessage(),"startDato skal være før slutDato eller der ikke givet lige mange tider" +
                " og antal enheder");
    }

    //TC33
    @Test
    void opretDagligSkaevOrdination3() {
        LocalTime[] klokkeslet = {LocalTime.of(10,30),LocalTime.of(14,0), LocalTime.of
                (22,0)};
        double[] antalEnheder = {2,1,4,3};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligSkaevOrdination(LocalDate.of(2025,3,14),LocalDate.of
                            (2025,3,17)
                    ,patient,laegemiddel,klokkeslet,antalEnheder);
        });
        assertEquals(exception.getMessage(),"startDato skal være før slutDato eller der ikke givet lige mange " +
                "tider og antal enheder");
    }

    // TC40
    @Test
    void ordinationPNAnvendt1() {
        PN pn = new PN(LocalDate.of(2025,3,13),LocalDate.of(2025,3,15),patient,laegemiddel,2);
        Controller.ordinationPNAnvendt(pn,LocalDate.of(2025,3,15));
        assertEquals(LocalDate.of(2025,3,15),pn.getDatoListe().getLast());
    }

    // TC41
    @Test
    void ordinationPNAnvendt2() {
        PN pn = new PN(LocalDate.of(2025,3,13),LocalDate.of(2025,3,13),patient,laegemiddel,2);
        Controller.ordinationPNAnvendt(pn,LocalDate.of(2025,3,13));
        assertEquals(LocalDate.of(2025,3,13),pn.getDatoListe().getLast());

    }

    // TC42
    @Test
    void ordinationPNAnvendt3() {
        PN pn = new PN(LocalDate.of(2025,3,13),LocalDate.of(2025,3,15),patient,laegemiddel,2);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.ordinationPNAnvendt(pn,LocalDate.of(2025,3,12)));
        assertEquals(exception.getMessage(),"Dato skal være inden for ordinations periode");
    }

    // TC43
    @Test
    void ordinationPNAnvendt4() {
        PN pn = new PN(LocalDate.of(2025,3,13),LocalDate.of(2025,3,13),patient,laegemiddel,2);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.ordinationPNAnvendt(pn,LocalDate.of(2025,3,14)));
        assertEquals(exception.getMessage(),"Dato skal være inden for ordinations periode");
    }

    //TC34
    @Test
    void anbefaletDosisPrDoegn24() {
        patient.setVaegt(24);

        assertEquals(96,Controller.anbefaletDosisPrDoegn(patient,laegemiddel));
    }

    //TC35
    @Test
    void anbefaletDosisPrDoegn25() {
        patient.setVaegt(25);

        assertEquals(125,Controller.anbefaletDosisPrDoegn(patient,laegemiddel));
    }

    //TC36
    @Test
    void anbefaletDosisPrDoegn70() {

        assertEquals(350,Controller.anbefaletDosisPrDoegn(patient,laegemiddel));
    }

    //TC37
    @Test
    void anbefaletDosisPrDoegn120() {
        patient.setVaegt(120);

        assertEquals(600,Controller.anbefaletDosisPrDoegn(patient,laegemiddel));
    }

    //TC38
    @Test
    void anbefaletDosisPrDoegn150() {
        patient.setVaegt(150);

        assertEquals(900,Controller.anbefaletDosisPrDoegn(patient,laegemiddel));
    }

    //TC44
    @Test
    void antalOrdinationerPrVægtPrLægemiddel() {
        Patient patient1 = new Patient("123456-7890","Hans Hansen",70);
        Patient patient3 = new Patient("123456-7890", "Jens Hansen", 81);
        patient.setVaegt(71);

        Ordination ordination1 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient1,laegemiddel,2);

        Ordination ordination2 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient,laegemiddel,2);

        Ordination ordination3 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient3,laegemiddel,2);

        assertEquals(2,Controller.antalOrdinationerPrVægtPrLægemiddel(60,80,laegemiddel));

    }

    //TC45
    @Test
    void antalOrdinationerPrVægtPrLægemiddel2() {
        Patient patient1 = new Patient("123456-7890","Hans Hansen",70);
        Patient patient3 = new Patient("123456-7890", "Jens Hansen", 81);
        patient.setVaegt(71);

        Ordination ordination1 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient1,laegemiddel,2);

        Ordination ordination2 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient,laegemiddel,2);

        Ordination ordination3 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient3,laegemiddel,2);

        assertEquals(0,Controller.antalOrdinationerPrVægtPrLægemiddel(20,50,laegemiddel));
    }

    //TC46
    @Test
    void antalOrdinationerPrVægtPrLægemiddel3() {
        Patient patient1 = new Patient("123456-7890","Hans Hansen",70);
        Patient patient3 = new Patient("123456-7890", "Jens Hansen", 81);
        patient.setVaegt(71);

        Ordination ordination1 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient1,laegemiddel,2);

        Ordination ordination2 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient,laegemiddel,2);

        Ordination ordination3 = Controller.opretPNOrdination(LocalDate.of(2025,3,13),LocalDate.of(2025,3,
                15),patient3,laegemiddel,2);

        assertEquals(0,Controller.antalOrdinationerPrVægtPrLægemiddel(50,20,laegemiddel));
    }
}