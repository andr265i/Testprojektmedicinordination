package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{

    private final ArrayList<Dosis> doser = new ArrayList<>();

    /**
     * Initialiserer en daglig skæv ordination med start dato, slut dato, patient,
     * lægemiddel, et array af tid og antalEnheder.
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     * Pre: alle tal i antalEnheder > 0
     * Pre: startdato er før slutdato
     * Pre: antallet af elementer i klokkeSlet og antalEnheder er ens
     */
    public DagligSkaev(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
                       LocalTime[] klokkeSlet, double[] antalEnheder) {
        super(startDen, slutDen, patient, laegemiddel);
        for (int i = 0; i < antalEnheder.length; i++) {
            opretDosis(klokkeSlet[i],antalEnheder[i]);
        }
    }

    //sammenhæng med dosis
    public ArrayList<Dosis> getDoser() {
        return new ArrayList<>(doser);
    }

    private void opretDosis(LocalTime tid, double antal) {
        Dosis dosis = new Dosis(tid, antal);
        doser.add(dosis);
    }

    //metoder
    @Override
    public double samletDosis() {
        return doegnDosis() * antalDage();
    }

    @Override
    public double doegnDosis() {
        double dagligDosis = 0;

        for (Dosis dosis : doser){
            dagligDosis += dosis.getAntal();
        }

        return dagligDosis;
    }

    @Override
    public String getType() {
        return "Daglig Skæv";
    }
}
