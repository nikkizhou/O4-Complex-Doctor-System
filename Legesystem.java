import java.util.Scanner;
import javax.sound.midi.MidiChannel;
import javax.swing.event.SwingPropertyChangeSupport;
import java.io.File;

public class Legesystem {
    // Opprett fire lister for aa holde styr av henholdvis leger,pasienter, leggemidler og resepter.
    private static Lenkeliste<Lege> alleLeger = new Prioritetskoe<>();
    private static IndeksertListe<Pasient> allePasienter = new IndeksertListe<Pasient>();
    private static IndeksertListe<Legemiddel> alleLgMidler = new IndeksertListe<Legemiddel>();
    private static IndeksertListe<Resept> alleResepter = new IndeksertListe<Resept>();

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        lesFraFil("innlesing.txt");

        int svar = -1;
        System.out.println(
                "\nMeny(Velg fra 0-4):\n1. Se alle pasienter, leger, legemidler og resepter.  \n2. Legg til nye elementer i systemet.  \n3. Bruk en resept \n4.Skrive ut forskjellige former for statistikk. \n0.Slutt programmet");
        svar = tallGyldighet(svar, 0, 4);

        while (svar != 0) {
            if (svar == 1) {
                skrivAlt();
            } else if (svar == 2) {
                leggTilElement();
            } else if (svar == 3) {
                brukResept();
            } else if (svar == 4) {
                visStatistikk();
            }
            System.out.println(
                    "\nMeny(Velg fra 0-4):\n1. Se alle pasienter, leger, legemidler og resepter.  \n2. Legg til nye elementer i systemet.  \n3. Bruk en resept \n4.Skrive ut forskjellige former for statistikk. \n0.Slutt programmet");
            
            // funksjonen tallGyldighet kjekker om svar er et tall, ogsaa om svar og inn i riktig omraade.
            svar = tallGyldighet(svar, 0, 4);
        }  
    }

    public static void lesFraFil(String filNavn) {

        Scanner fil = null;
        // categoryListe er ment for aa identifisere alle linje med #, dvs alle linje med kategori som pasienter, resepter....
        String[] categoryListe = null;

        try {
            fil = new Scanner(new File(filNavn));

        } catch (Exception e) {
            System.out.println("Kan ikke lese" + filNavn);
            System.exit(1);
        }

        while (fil.hasNextLine()) {
            String linje = fil.nextLine();

            if (linje.charAt(0) == '#') {
                categoryListe = linje.split(" "); // ["#", "Pasienter", "(navn"]

            // hvis linjen starter ikke med #, saa opprette en annen liste som blir split av ','.
            } else {
                String[] liste = linje.split(","); // ["Jens Hans Olsen","11111143521"]

                // legg alle linjer som beskriver pasienter inni listen allePasienter.
                if (categoryListe[1].equals("Pasienter")) {
                    Pasient pasient = new Pasient(liste[0], liste[1]);
                    allePasienter.leggTil(pasient);

                // legg alle linjer som beskriver Legemiddel inni listen alleLgMidler basert paa hvilke typer de er.
                } else if (categoryListe[1].equals("Legemidler")) {
                    String navn = liste[0];
                    int pris = Integer.parseInt(liste[2]);
                    double virkestoff = Double.parseDouble(liste[3]);
                    

                    if (liste[1].equals("narkotisk")) {
                        Narkotisk narkotisk = new Narkotisk(navn, pris, virkestoff, Integer.parseInt(liste[2]));
                        alleLgMidler.leggTil(narkotisk);

                    } else if (liste[1].equals("vanedannende")) {
                        Vanedannende vanedannende = new Vanedannende(navn, pris, virkestoff,Integer.parseInt(liste[2]));
                        alleLgMidler.leggTil(vanedannende);

                    } else if (liste[1].equals("vanlig")) {
                        Vanlig vanlig = new Vanlig(navn, pris, virkestoff);
                        alleLgMidler.leggTil(vanlig);
                    }
                
                 // legg alle linjer som beskriver leger inni listen alleLeger basert paa hvilke typer de er.
                } else if (categoryListe[1].equals("Leger")) {
                    if (liste[1].equals("0")) {
                        Lege lege = new Lege(liste[0]);
                        alleLeger.leggTil(lege);
                    } else {
                        Spesialister spesialister = new Spesialister(liste[0], liste[1]);
                        alleLeger.leggTil(spesialister);
                    }

                // lag objecter av Lege, Pasient, Legemiddel, og sette dem inni listen alleResepter
                } else if (categoryListe[1].equals("Resepter")) {
                    Resept resept = null;
                    Legemiddel legemiddel = alleLgMidler.hent(Integer.parseInt(liste[0]) - 1);
                    Pasient pasient = allePasienter.hent(Integer.parseInt(liste[2]) - 1);
                    int reit = Integer.parseInt(liste[0]); //!!!!!!!!
                    String reseptType = liste[3];
                    boolean legeFinnes = false;

                    // iterer alle lege objekter i listen alleLeger
                    for (Lege lege : alleLeger) {
                        //hvis en av legene i listen alleLeger har samme navn som den i filen
                        if (liste[1].equals(lege.hentNavn())) {
                            legeFinnes = true;
                            // hvis det er en militaer resept, sett reit til 3.
                            if (liste[3].equals("militaer")) {
                                resept = skrivResept(reseptType, legemiddel, pasient, 3, lege);
                            } else {
                                resept = skrivResept(reseptType, legemiddel, pasient, reit, lege);
                            }
                        }
                    }
                    // hvis ingen av legene i listen alleLeger har samme navn som den i filen, skriv tilbakemelding.
                    if (!legeFinnes) {
                        System.out.println(
                                "Lege " + liste[1] + " er ikke registrert i systemet. Kan ikke legge resept.");
                        return;
                    }

                    alleResepter.leggTil(resept);

                    // register den nye resepten(som er nylig lagt i listen alleResepter) til tilsvarende pasient.
                    pasient.leggNyResept(resept);
                }
            }
        }
    }

    public static Resept skrivResept(String reseptType, Legemiddel legemiddel, Pasient pasient, int reit, Lege lege) {
        Resept resept = null;

        try {
            if (reseptType.equals("hvit")) {
                resept = lege.skrivHvitResept(legemiddel, pasient, reit);
            } else if (reseptType.equals("blaa")) {
                resept = lege.skrivBlaaResept(legemiddel, pasient, reit);
            } else if (reseptType.equals("p")) {
                resept = lege.skrivPResept(legemiddel, pasient, reit);
            } else if (reseptType.equals("militaer")) {
                resept = lege.skrivMilResept(legemiddel, pasient);
            }

        } catch (UlovligUtskrift e) {
            System.out.println(e);
        }

        return resept;

    }

    public static void skrivAlt() {
        System.out.println("\n[Alle leger]:");
        skrivElement(alleLeger);

        System.out.println("\n[Alle pasienter]: ");
        skrivElement(allePasienter);

        System.out.println("\n[Alle legemidler]: ");
        skrivElement(alleLgMidler);

        System.out.println("\n[Alle resepter]: ");
        skrivElement(alleResepter);

    }

    public static void skrivElement(Lenkeliste listen) {
        for (int i = 0; i < listen.stoerrelse(); i++) {
            System.out.println(listen.hent(i).toString());
        }
    }

    public static int tallGyldighet(int tall, int min, int maks) {
        try {
            tall = Integer.parseInt(sc.nextLine());

        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        while (tall < min || tall > maks) {
            System.out.println("Ugyldig input. Velg fra " + Integer.toString(min) + '-' + Integer.toString(maks));
            tall = Integer.parseInt(sc.nextLine());
        }

        return tall;

    }

    public static void leggTilElement() {
        int tall = 0;
        System.out.println("\nHva vil du legge til? \nVelg fra 1-4: 1.Pasient  2.Legemiddel  3.Lege  4.Resept");
        tall = tallGyldighet(tall, 1, 4);

        if (tall == 1) {
            System.out.println("Oppgi navnet til pasienten: ");
            String navn = sc.nextLine().trim();
            System.out.println("Oppgi foedselnummeret til pasienten: ");
            String fNummer = sc.nextLine().trim();

            Pasient pasienten = new Pasient(navn, fNummer);
            allePasienter.leggTil(pasienten);

            System.out.println("\nPasienten " + pasienten.hentNavn()
                    + " er naa lagt inni systemet!! \nOversikt til alle pasienter: ");
            skrivElement(allePasienter);

        } else if (tall == 2) {
            Legemiddel legemiddel = null;
            int type = 0;
            int pris = -1;

            System.out.println("Oppgi typen til legemidlen(velg fra 1-3): 1.Narkotiske 2.Vanedannende 3.Vanlig");
            type = tallGyldighet(type, 1, 3);

            System.out.println("Oppgi navnet til legemidlen: ");
            String navn = sc.nextLine();

            System.out.println("Oppgi prisen til legemidlen: ");
            pris = tallGyldighet(pris, 0, 10000000);

            System.out.println("Oppgi virkestoff til legemidlen: ");
            Double virkestoff = Double.parseDouble(sc.nextLine());

            if (type == 3) {
                legemiddel = new Vanlig(navn, pris, virkestoff);

            } else {
                int styrke = -1;
                System.out.println("Oppgi styrken til legemidlen: ");
                styrke = tallGyldighet(styrke, 0, 10000000);

                if (type == 1) {
                    legemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
                } else if (type == 2) {
                    legemiddel = new Vanedannende(navn, pris, virkestoff, styrke);
                }
            }

            alleLgMidler.leggTil(legemiddel);
            System.out.println("Legemidlen med ID " + legemiddel.hentId()
                    + " er naa lagt inni systemet!! \nOversikt til alle legemiddel:");
            skrivElement(alleLgMidler);
            System.out.println();

        } else if (tall == 3) {
            Lege lege;

            System.out.println("Oppgi navnet til legen: ");
            String navn = sc.nextLine();
            System.out.println("Oppgi kontroll ID til legen. Skriv 0 for vanlig lege.");
            String kontrollID = sc.nextLine();

            if (kontrollID.equals("0")) {
                lege = new Lege(navn);
            } else {
                lege = new Spesialister(navn, kontrollID);
            }

            alleLeger.leggTil(lege);
            System.out.println("Legen " + lege.hentNavn()
                    + " er naa lagt inni systemet!! \nOversikt til alle leger: \n");
            skrivElement(alleLeger);
            System.out.println();

        } else if (tall == 4) {
            Resept resept = null;
            Lege legeTilRes = null;
            Pasient pasientTilRes = null;
            Legemiddel middelTilRes = null;

            System.out.println("Oppgi navnet til legen som skrev ut resepten: ");
            String legeNavn = sc.nextLine();
            
            boolean legeFinnes = false;
            while (!legeFinnes) {
                for (Lege lege : alleLeger) {
                    if (lege.hentNavn().equals(legeNavn)) {
                        legeTilRes = lege;
                        legeFinnes = true;
                    }
                }

                if (!legeFinnes) {
                    System.out.println("Legen " + legeNavn + " er ikke registrert i systemet. Skriv paa nytt: ");
                    legeNavn = sc.nextLine();
                }
            }

            System.out.println("Oppgi pasient ID til pasienten som faar resepten: ");
            int pasientID = Integer.parseInt(sc.nextLine());
            
            boolean pasientFinnes = false;
            while (!pasientFinnes) {
                for (Pasient pasient : allePasienter) {
                    if (pasient.hentId() == pasientID) {
                        pasientTilRes = pasient;
                        pasientFinnes = true;
                    }
                }

                if (!pasientFinnes) {
                    System.out.println(
                            "Pasientent med ID " + pasientID + " er ikke registrert i systemet.Skriv pa nytt: ");
                    pasientID = Integer.parseInt(sc.nextLine());
                }
            }

            System.out.println("Oppgi legemiddel ID til legemidlen paa resepten: ");
            int legemiddelID = Integer.parseInt(sc.nextLine());
        
            
            boolean lmFinnes = false;
            while (!lmFinnes) {
                for (Legemiddel lm : alleLgMidler) {
                    if (lm.hentId() == legemiddelID) {
                        middelTilRes = lm;
                        lmFinnes = true;
                    }
                }

                if (!lmFinnes) {
                    System.out.println(
                            "Pasientent med ID " + legemiddelID + " er ikke registrert i systemet.Skriv pa nytt: ");
                    legemiddelID = Integer.parseInt(sc.nextLine());
                }
            }

            System.out.println("Oppgi type til resepten, skriv:'hvit', 'blaa', 'p' eller 'militaer'");
            String reseptType = sc.nextLine().trim();

            while (!reseptType.equals("hvit") && !reseptType.equals("blaa")
                    && !reseptType.equals("p")
                    && !reseptType.equals("militaer")) {
                System.out.println("Ugyldig input. Skriv:'hvit', 'blaa', 'p' eller 'militaer'.");
                reseptType = sc.nextLine().trim();
            }

            if (reseptType.equals("militaer")) {
                resept = skrivResept(reseptType, middelTilRes, pasientTilRes, 3, legeTilRes);
            } else {
                int reit = 0;
                System.out.println("Oppgi reit til resepten, velg fra 1-10: ");
                reit = tallGyldighet(reit, 1, 10);

                resept = skrivResept(reseptType, middelTilRes, pasientTilRes, reit, legeTilRes);
            }

            alleResepter.leggTil(resept);
            pasientTilRes.leggNyResept(resept);

            System.out.println("Resepten med ID " + resept.hentId()
                    + " er naa lagt inni systemet!! \nOversikt til alle resepter:");
            skrivElement(alleResepter);
            System.out.println();
        }
    }


    public static void brukResept() {

        System.out.println("\nHvilken pasient vil du se resepter for?");
        Pasient pasient = null;
        int antResepter = 0;
        int svar1 = -1;

        for (int i = 0; i < allePasienter.stoerrelse(); i++) {
            pasient = allePasienter.hent(i);
            System.out.println(i + ": " + pasient.hentKortInfo());
        }
        svar1 = tallGyldighet(svar1, 0, allePasienter.stoerrelse() - 1);

        pasient = allePasienter.hent(svar1);
        antResepter = pasient.alleResepter().size();
        System.out.println("Valgt pasient: " + pasient.hentKortInfo() + ", Antall resepter: " + antResepter);

        if (antResepter == 0) {
            System.out.println("Pasienten har ikke noen resept.");
            return;
        }

        System.out.println("\nHvilken resept vil du bruke?");
        Resept resept = null;
        int svar2 = -1;
        for (int i = 0; i < pasient.alleResepter().size(); i++) {
            resept = pasient.alleResepter().get(i);
            System.out.println(i + ": " + resept.hentKortInfo());
        }
        svar2 = tallGyldighet(svar2, 0, antResepter - 1);
        resept = pasient.alleResepter().get(svar2);

        String lmNavn = resept.hentLegemiddel().hentNavn();

        if (!resept.bruk()) {
            System.out.println("Kunne ikke bruke resept paa" + lmNavn + "(ingen gjenvaerende reit).");

        } else {
            System.out.println("Brukte resept paa" + lmNavn + ". Antall gjenvaerende reit: "
                    + resept.hentReit());
        }

        return;
    }

    public static int regnAntLm(String type) {
        int antallNar = 0;
        int antallVane = 0;
        int returTall = 0;

        for (Resept resept : alleResepter) {
            Legemiddel lm = resept.hentLegemiddel();

            if (lm instanceof Vanedannende) {
                antallVane++;
            } else if (lm instanceof Narkotisk) {
                antallNar++;
            }
        }
        if (type.equals("Vanedannende")) {
            returTall = antallVane;
        } else if (type.equals("Narkotisk")) {
            returTall = antallNar;
        }

        return returTall;
    }

    public static void visStatistikk() {
        System.out.println("\nHva vil du se paa? Velg fra 1-4: ");
        System.out.println("1. Totalt antall utskrevne resepter paa vanedannende legemidler.");
        System.out.println("2. Totalt antall utskrevne resepter paa narkotiske legemidler.");
        System.out.println(
                "3. Alle leger som har gitt resept paa narkotiske legemidler, og antallet slike resepter per lege.");
        System.out.println(
                "4. Alle pasienter som har gyldig resept paa narkotiske legemidler og antallet for per pasient");

        int svar = 0;
        svar = tallGyldighet(svar, 1, 4);

        if (svar == 1) {
            System.out.println("Totalt antall utskrevne resepter paa vanedannende legemidler: " + regnAntLm("Vanedannende"));

        } else if (svar == 2) {
            System.out.println("Totalt antall utskrevne resepter paa narkotiske legemidler: " + regnAntLm(
                    "Narkotisk"));

        } else if (svar == 3) {
            // denne listen holder styr paa alle leger som har skrevet Narkotisk
            IndeksertListe<Lege> legeListe = new Prioritetskoe<>();
            // denne listen holder styr paa tilsvarende antall  Narkotisk resepter legene har.
            IndeksertListe<Integer> antallListe = new IndeksertListe<>();

            //iterer alle resepter i listen alleResepter
            for (Resept resept : alleResepter) {
                Legemiddel lm = resept.hentLegemiddel();
                boolean legeFinnesIListe = false;

                //hvis legemiddlen i resepten er Narkotisk
                if (lm instanceof Narkotisk) {

                    // saa iterer alle lege objekter i listen med lege som har narkotisk resepter.
                    for (int i = 0; i < legeListe.stoerrelse(); i++) {
                        // hvis navnet til legen paa resepten er i legelisten allerede
                        if (resept.hentLege().hentNavn().equals(legeListe.hent(i).hentNavn())) {
                            // saa tilsvarende tall i antallListe oeker med 1.
                            antallListe.sett(i, antallListe.hent(i) + 1);
                            legeFinnesIListe = true;
                        }

                    }
                    if (legeFinnesIListe == false) {
                        legeListe.leggTil(resept.hentLege());
                        antallListe.leggTil(1);
                    }
                }
            }
            System.out.println("Alle lege som har gitt narkotiske legemidler:");

            if (!legeListe.erTom()) {
                for (int i = 0; i < legeListe.stoerrelse(); i++) {
                    System.out.println("Lege: " + legeListe.hent(i).hentNavn()
                            + ", Antall resepter med narkotiske legemidler: " + Integer.toString(antallListe.hent(i)));
                }
            } else {
                System.out.println("Ingen leger i systemet har gitt resept paa narkotiske legemidler");
            }
      
        } else if (svar == 4) {

            IndeksertListe<Pasient> pListe = new IndeksertListe<>();
            IndeksertListe<Integer> antallListe = new IndeksertListe<>();

            for (Resept resept : alleResepter) {
                Legemiddel lm = resept.hentLegemiddel();
                boolean pasientFinnesIListe = false;

                if (lm instanceof Narkotisk) {

                    for (int i = 0; i < pListe.stoerrelse(); i++) {
                        if (resept.hentPasient().hentNavn().equals(pListe.hent(i).hentNavn())) {
                            antallListe.sett(i, antallListe.hent(i) + 1);
                            pasientFinnesIListe = true;
                        }
                    }
                    if (pasientFinnesIListe == false) {
                        pListe.leggTil(resept.hentPasient());
                        antallListe.leggTil(1);
                    }
                }
            }
            System.out.println(pListe.stoerrelse());
            System.out.println("Alle pasienter som har gitt narkotiske legemidler:");

            if (!pListe.erTom()) {
                for (int i = 0; i < pListe.stoerrelse(); i++) {
                    System.out.println("Pasient: " + pListe.hent(i).hentNavn()
                            + ", Antall resepter med narkotiske legemidler: " + Integer.toString(antallListe.hent(i)));
                }
            } else {
                System.out.println("Ingen leger i systemet har gitt resept paa narkotiske legemidler");
            }
        }
    }
}
        
