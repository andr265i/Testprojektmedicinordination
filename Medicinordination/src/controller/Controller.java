package controller;

import ordination.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class Controller {
	private static Storage storage;

	public static void setStorage(Storage storageIn) {
		storage = storageIn;
	}

	/**
	 * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
	 * ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: antal > 0
	 * @return opretter og returnerer en PN ordination.
	 */
	public static PN opretPNOrdination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double antal) {

		PN pn;

		if (startDen.isBefore(slutDen)) {
			pn = new PN(startDen,slutDen,patient,laegemiddel,antal);

		} else {
			throw new IllegalArgumentException("startDato er efter slutDato");
		}

		return pn;
	}

	/**
	 * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: margenAntal, middagAntal, aftanAntal, natAntal >= 0
	 */
	public static DagligFast opretDagligFastOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			double morgenAntal, double middagAntal, double aftenAntal,
			double natAntal) {
		
		DagligFast dagligFast;
		
		if(startDen.isBefore(slutDen)) {
			dagligFast = new DagligFast(startDen,slutDen,patient,laegemiddel,morgenAntal,middagAntal,aftenAntal,
					natAntal);
		}
		else {
			throw new  IllegalArgumentException ("startDato er efter slutDato");
		}

		return dagligFast;
	}

	/**
	 * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke.
	 * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige kastes også en IllegalArgumentException.
	 *
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: alle tal i antalEnheder > 0
	 */
	public static DagligSkaev opretDagligSkaevOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			LocalTime[] klokkeSlet, double[] antalEnheder) {

		if (startDen.isBefore(slutDen) && klokkeSlet.length == antalEnheder.length){
			return new DagligSkaev(startDen, slutDen, patient, laegemiddel, klokkeSlet, antalEnheder);
		} else {
			throw new IllegalArgumentException("startDato er efter slutDato eller der ikke givet lige mange tider og antal enheder");
		}
	}

	/**
	 * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
	 * datoen ikke er indenfor ordinationens gyldighedsperiode kastes en
	 * IllegalArgumentException
	 * Pre: ordination og dato er ikke null
	 */
	public static void ordinationPNAnvendt(PN ordination, LocalDate dato) {
		if (dato.isAfter(ordination.getStartDen().minusDays(1)) && dato.isBefore(ordination.getSlutDen().plusDays(1))){
			ordination.givDosis(dato);
		} else {
			throw new IllegalArgumentException("Dato ikke inden for ordinations periode");
		}
	}

	/**
	 * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
	 * til patientens vægt). Det er en forskellig enheds faktor der skal
	 * anvendes, og den er afhængig af patientens vægt.
	 * Pre: patient og lægemiddel er ikke null
	 */
	public static double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
		double anbefaletDosisPrDoegn;

		if(patient.getVaegt() < 25 ) {
			anbefaletDosisPrDoegn = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnLet();
		}
		else if (patient.getVaegt() > 120) {
			anbefaletDosisPrDoegn = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnTung();
		}
		else {
			anbefaletDosisPrDoegn = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnNormal();
		}
		return anbefaletDosisPrDoegn;
	}

	/**
	 * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
	 * ordinationer.
	 * Pre: laegemiddel er ikke null
	 */
	public static int antalOrdinationerPrVægtPrLægemiddel(double vægtStart, double vægtSlut, Laegemiddel laegemiddel) {
		int antal = 0;

		for (Patient p : storage.getAllPatienter()) {
			for (Ordination o : p.getOrdinationer()) {
				if ((p.getVaegt() >= vægtStart && p.getVaegt() <= vægtSlut) && o.getLaegemiddel() == laegemiddel) {
					antal++;
				}
			}
		}

		return antal;
	}

	public static List<Patient> getAllPatienter() {
		return storage.getAllPatienter();
	}

	public static List<Laegemiddel> getAllLaegemidler() {
		return storage.getAllLaegemidler();
	}

	/**
	 * Metode der kan bruges til at checke at en startDato ligger før en
	 * slutDato.
	 *
	 * @return true hvis startDato er før slutDato, false ellers.
	 */
	private static boolean checkStartFoerSlut(LocalDate startDato, LocalDate slutDato) {
		boolean result = true;
		if (slutDato.compareTo(startDato) < 0) {
			result = false;
		}
		return result;
	}

	public static Patient opretPatient(String cpr, String navn, double vaegt) {
		Patient p = new Patient(cpr, navn, vaegt);
		storage.addPatient(p);
		return p;
	}

	public static Laegemiddel opretLaegemiddel(String navn,
			double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal,
			double enhedPrKgPrDoegnTung, String enhed) {
		Laegemiddel lm = new Laegemiddel(navn, enhedPrKgPrDoegnLet,
				enhedPrKgPrDoegnNormal, enhedPrKgPrDoegnTung, enhed);
		storage.addLaegemiddel(lm);
		return lm;
	}



}
