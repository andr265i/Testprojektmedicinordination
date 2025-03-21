package ordination;

import java.time.LocalTime;

public class Dosis {
    private LocalTime tid;
    private double antal;

    /**
     * Initialiserer en dosis med tid og antal.
     * Pre: antal >= 0
     * Pre: tid.hour = 0-23 og tid.minut = 0-59
     */
    Dosis(LocalTime tid, double antal) {
        this.tid = tid;
        this.antal = antal;
    }

    public double getAntal() {
        return antal;
    }

    public void setAntal(double antal) {
        this.antal = antal;
    }

    public LocalTime getTid() {
        return tid;
    }

    public void setTid(LocalTime tid) {
        this.tid = tid;
    }

    @Override
    public String toString(){
        return "Kl: " + tid + "   antal:  " + antal;
    }

}
