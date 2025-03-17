package controller;

import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.awt.*;
import java.time.LocalDate;

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

    @Test
    void opretDagligFastOrdination() {
    }

    @Test
    void opretDagligSkaevOrdination() {
    }

    @Test
    void ordinationPNAnvendt() {
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

    @Test
    void antalOrdinationerPrVægtPrLægemiddel() {
    }
}