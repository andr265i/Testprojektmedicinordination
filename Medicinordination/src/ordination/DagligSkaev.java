package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{

    private final ArrayList<Dosis> doser = new ArrayList<>();

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

    //ved ikke om skal slettes
    public void removeDosis (Dosis dosis){
        if (doser.contains(dosis)){
            doser.add(dosis);
        }
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
