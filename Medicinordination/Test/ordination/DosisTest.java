package ordination;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DosisTest {

    // TC 1
    @Test
    void dosis() {
        Dosis dosis = new Dosis(LocalTime.of(12, 0), 2);
        assertNotNull(dosis);
        assertEquals(LocalTime.of(12, 0), dosis.getTid());
        assertEquals(2, dosis.getAntal());
    }

}