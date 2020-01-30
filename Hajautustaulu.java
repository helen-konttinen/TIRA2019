package Harjoitustyo;

public class Hajautustaulu {
    //"dummy" -muuttuja
    public static final String SENTINEL = "NO_KEY";
    // hajautusfunktion satunnaiset avaimet
    public static final int a = (int)(Math.random() * 6) + 1;
    public static final int b = (int)(Math.random() * 6) + 1;

    public class Node {
        private Object data;
        private int syotetiedosto;
        private Node seuraava;
        private Node edellinen;

        /* Oletusrakentaja.*/
        public Node (Object x, int source) {
            data = x;
            syotetiedosto = source;
            seuraava = edellinen = null;
        }

        /* Setterit*/
        public void data (Object x) {
            data = x;
        }

        public void asetaSeuraava (Node s) {
            seuraava = s;
        }

        public void asetaEdellinen (Node e) {
            edellinen = e;
        }

        /* Getterit*/
        public Object data () {
            return data;
        }

        public Node seuraava () {
            return seuraava;
        }

        public Node edellinen () {
            return edellinen;
        }
    }

    public class Lokero {
        
        private int koko;
        private Node paa;
        private Node hanta;

        public void lisaaNode (Node lisattava) {
            //jos koko on 0, eli lokero on tyhjä,
            //asetetaan lisättävä alkio viittaamaan pää- ja häntä-lokeroihin
            if (koko == 0) {
                paa = hanta = lisattava;
            }
            else {
                //otetaan talteen häntä -lokero, ja asetetaan lisättävä alkio
                //viittaamaan häntä -lokeroon.
                Node valiaikainen = hanta;
                hanta = lisattava;
                //asetetaan lisättävän edelliseksi lokeroksi entinen häntä
                //ja entisen hännän seuraavaksi lokeroksi lisättävä
                lisattava.asetaEdellinen(valiaikainen);
                valiaikainen.asetaSeuraava(lisattava);
                koko++; 
            }
        }

        public Node poistaPaa () {
            Node valiaikainen = paa;
            //jos taulun koko on 1, lisätään uusi "NO_KEY" -arvolla
            //varustettu lokero pääksi ja hännäksi
            if (koko == 1) {
                paa = hanta = new Node(SENTINEL, 0);
            } 
            //muuten "siirretään" pää seuraavaksi lokeroksi
            else {
                paa = paa.seuraava();
            }
            koko--;
            //palautetaan poistettu lokero
            return valiaikainen;
        }

        /* Rakentajat */
        public Lokero() {
            koko = 0;
            paa = hanta = new Node(SENTINEL, 0);
        }
        
        /* Setterit */
        public void koko (int k) {
            koko = k;
        }
        
        public void paa (Node p) {
            paa = p;
        }
        
        public void hanta (Node h) {
            hanta = h;
        }

        /* Getterit*/
        public int koko () {
            return koko;
        }

        public Node paa () {
            return paa;
        }

        public Node hanta () {
            return hanta;
        }
    }

    public class TiraTaulu {

        private int koko;
        private int nodes;
        private Lokero[] taulu;
        //täyttöaste
        private double loadfactor = nodes/koko;
        //täyttöasteen raja
        private double loadfactor_limit;

        public void lisaa (Node input) {
            int apumuuttuja = Integer.parseInt(input.data().toString());
            Lokero paikka = taulu[hajautusfunktio(apumuuttuja, koko)];
            paikka.lisaaNode(input);
            nodes++;

            if (loadfactor > loadfactor_limit) {
                taulu = uudelleenHajauta(taulu);
            }
        }

        public Lokero[] uudelleenHajauta (Lokero[] taulukko) {
            Lokero tempTaulukko = new Lokero();
            for (int i = 0; i < koko; i++) {
                if (taulukko[i].koko() > 0) {
                    while(taulkko[i].paa().data() != SENTINEL) {
                        tempTaulukko.lisaaNode(taulukko[i].poistaPaa());
                    }
                }
            }

            if (onTyhja()) {
                koko = koko*2;
                TiraTaulu valiaikainen = new TiraTaulu(koko);
                while (tempTaulukko.paa().data() != SENTINEL) {
                    valiaikainen.lisaa(tempTaulukko.poistaPaa());
                }
            }
            return valiaikainen;
        }

        public boolean onTyhja () {
            for (int i = 0; i < koko; i++) {
                if (taulu[i].hanta().data() != SENTINEL){
                    return false;
                }
            }
            return true;
        }

        /* Rakentajat.*/
        public TiraTaulu () {
            koko = 5;
            this.taulu = new Lokero[koko];
            nodes = 0;
            loadfactor_limit = 0.75;
        }

        public TiraTaulu (int k) {
            koko = k;
            this.taulu = new Lokero[koko];
            nodes = 0;
            loadfactor_limit = 0.75;
        }

        /* Getters.*/
        public int koko() {
            return koko;
        }

        public int nodes() {
            return nodes;
        }

        /*Setters.*/
        public void setKoko (int k) {
            koko = k;
        }

    }

    public static void main(String[] args){
        TiraTaulu hajautustaulu = new TiraTaulu();   // päähajautustaulu
        lisaaTaulukko(setA, 1);
        lisaaTaulukko(setB, 2);
    }

        public void lisaaTaulukko(Object[] taulukko, int x){
        
            for (int i = 0; i < taulukko.length; i++) {
                Node valiaikainen = new Node(taulukko[i], x);
                hajautustaulu.lisaa(valiaikainen);
            }
        }

    /* hajautusfunktio taulukon k�sittelyn avuksi */
    public int hajautusfunktio(int input, int s){
        return (a * input + b) % s;
    }
}