package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Ordination {
    private LocalDate startDen;
    private LocalDate slutDen;

    private Laegemiddel laegemiddel;

    public Ordination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel) {
        this.slutDen = slutDen;
        this.startDen = startDen;
        this.laegemiddel = laegemiddel;
        patient.addOrdination(this);
    }

    public LocalDate getStartDen() {
        return startDen;
    }	

    public LocalDate getSlutDen() {
        return slutDen;
    }

    /**
     * Antal hele dage mellem startdato og slutdato. Begge dage inklusive.
     * @return antal dage ordinationen gælder for
     */
    public int antalDage() {
        return (int) ChronoUnit.DAYS.between(startDen, slutDen) + 1;
    }

    //sammenhæng med laegemiddel
    public Laegemiddel getLaegemiddel() {
        return laegemiddel;
    }

    public void setLaegemiddel(Laegemiddel laegemiddel) {
        if (this.laegemiddel != laegemiddel) {
            this.laegemiddel = laegemiddel;
        }
    }

    @Override
    public String toString() {
        return startDen.toString();
    }

    /**
     * Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
     * @return
     */
    public abstract double samletDosis();

    /**
     * Returnerer den gennemsnitlige dosis givet pr dag i den periode ordinationen er gyldig
     * @return
     */
    public abstract double doegnDosis();

    /**
     * Returnerer ordinationstypen som en String
     * @return
     */
    public abstract String getType();
}
