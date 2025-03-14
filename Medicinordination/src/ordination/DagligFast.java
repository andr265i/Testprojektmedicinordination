package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class DagligFast extends Ordination {

    private final Dosis[] doser = new Dosis[4];

    /**
     * Initialiserer en daglig fast ordination med start dato, slut dato, patient,
     * lægemiddel og antal morgen, middag, aften og nat.
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     * Pre: margenAntal, middagAntal, aftanAntal, natAntal >= 0
     * Pre: startdato er før slutdato
     */
    public DagligFast(LocalDate startDen, LocalDate slutDen, Patient patient,
                      Laegemiddel laegemiddel, double morgenAntal,
                      double middagAntal, double aftenAntal, double natAntal) {

        super(startDen, slutDen, patient, laegemiddel);
        doser[0] = createDosis(LocalTime.of(6, 0), morgenAntal);
        doser[1] = createDosis(LocalTime.of(12, 0), middagAntal);
        doser[2] = createDosis(LocalTime.of(18, 0), aftenAntal);
        doser[3] = createDosis(LocalTime.of(0, 0), natAntal);
    }

    private Dosis createDosis(LocalTime tid, double antal) {
        Dosis dosis = new Dosis(tid, antal);
        return dosis;
    }

    public Dosis[] getDoser() {
        return doser.clone();
    }


    @Override
    public double samletDosis() {
        return doegnDosis() * antalDage();
    }

    @Override
    public double doegnDosis() {
        double dognDosis = 0;

        for (Dosis dosis : doser) {
            dognDosis += dosis.getAntal();
        }
        return dognDosis;
    }

    @Override
    public String getType() {
        return "Daglig Fast";
    }
}
