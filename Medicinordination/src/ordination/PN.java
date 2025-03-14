package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PN extends Ordination {

    private double antalEnheder;
    private List<LocalDate> datoListe = new ArrayList<>();

    /**
     * Initialiserer en daglig fast ordination med start dato, slut dato, patient,
     * lægemiddel og antal enheder.
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     * Pre: antal > 0
     * Pre: startdato er før slutdato
     */
    public PN(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double antalEnheder) {
        super(startDen, slutDen, patient, laegemiddel);
        this.antalEnheder = antalEnheder;
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     *
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        boolean gyldigDosis = false;
        if (givesDen.isAfter(super.getStartDen().minusDays(1)) && givesDen.isBefore(super.getSlutDen().plusDays(1))) {
            gyldigDosis = true;
            datoListe.add(givesDen);
        }
        return gyldigDosis;
    }

    public double doegnDosis() {
        double dosis = (getAntalGangeGivet() * antalEnheder) / antalDage();
        return dosis;
    }

    @Override
    public String getType() {
        return "PN";
    }

    @Override
    public double samletDosis() {
        double samletDosis = doegnDosis() * antalDage();
        return samletDosis;
    }

    /**
     * Antal hele dage mellem første og sidste givning. Begge dage inklusive.
     * @return antal dage ordinationen er givet inden for
     */
    @Override
    public int antalDage() {
        LocalDate min = LocalDate.MAX;
        LocalDate max = LocalDate.MIN;

        if (!datoListe.isEmpty()) {
            for (LocalDate d : datoListe) {
                if (d.isBefore(min)) {
                    min = d;
                }
                if (d.isAfter(max)) {
                    max = d;
                }
            }
            return (int) ChronoUnit.DAYS.between(min, max) + 1;
        }
        return 0;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     *
     * @return
     */
    public int getAntalGangeGivet() {
        int antal = datoListe.size();
        return antal;
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
