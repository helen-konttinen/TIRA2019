/*
 * Tietorakenteet 2019 -kurssin harjoitustyö.
 * Harjoitustyössä suoritetaan joukko -operaatioita tiedostoille
 * käyttäen apuna hajautustaulua.
 * 
 * Helen Konttinen
 * helen.konttinen@tuni.fi
 * */

import java.lang.Integer;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Tira2019 {
    /* pääsilmukka joka ajaa harjoitustyötä*/
    public void run() {
        System.out.println("Harjoitustyö käsittelee numeroita kokonaislukuina. Hajautusfunktiona käytetään (+1),");
        System.out.println("mikä tarkoittaa, että ylivuoto on mahdollista jos yrittää antaa arvoksi");
        System.out.println("Integer.MAX_VALUE kahdesti peräkkäin tai jos antaa arvoksi ryhmän arvoja, jotka");
        System.out.println("ylittäisivät Integer.MAX_VALUE.\n");
        
        TiraTaulu htA = new TiraTaulu();
        int[] setA = lueInput("setA.txt");
        
        TiraTaulu htB = new TiraTaulu();
        int[] setB = lueInput("setB.txt");
        
        boolean run = true;
        Scanner sc = new Scanner(System.in);

        while (run) {
            System.out.println("\nKäytä komentoja \"or\", \"and\" ja \"xor\" toimintojen suorittamiseksi.");
            System.out.println("Voit poistua ohjelmasta \"exit\" -komennolla.");
            String s = sc.nextLine();
            String command = s.substring(0, 1);
            
            if ((s.charAt(0) == 'e') && (s.charAt(1) == 'x') && (s.charAt(2) == 'i') && 
            (s.charAt(3) == 't') && (s.length() == 4)) {
                run = false;
            } 
            else {
                //importoi data txt-tiedostoista jos käyttäjä ei halua poistua ohjelmasta
                htA.lisaaTaulukko(setA, 1);
                htB.lisaaTaulukko(setB, 2);
                
                if ((s.charAt(0) == 'o') && (s.charAt(1) == 'r') && (s.length() == 2)) {
                    union(htA, htB);
                } 
                else if ((s.charAt(0) == 'a') && (s.charAt(1) == 'n') && (s.charAt(2) == 'd') && (s.length() == 3)) {
                    intersection(htA, htB);
                } 
                else if ((s.charAt(0) == 'x') && (s.charAt(1) == 'o') && (s.charAt(2) == 'r') && (s.length() == 3)) {
                    exclude(htA, htB);
                } 
                else {
                    System.out.println("Väärä syöte");
                }
            }
        }
        sc.close();
        System.out.println("\nHarjoitustyö suljettu.");
    }

    // and-toiminto
    public void union(TiraTaulu htA, TiraTaulu htB) {
        TiraTaulu A = htA;
        TiraTaulu B = htB; 
        TiraTaulu C = new TiraTaulu();
        
        while ((A.koko() > 0) && (B.koko() > 0)) {
           if (A.paa().node().data() > B.paa().node().data()) {
              C.lisaaLokero(A.poistaPaa());
           } 
           else {
              C.lisaaLokero(B.poistaPaa());
           }
        }
        while (A.koko() > 0) {
           C.lisaaLokero(A.poistaPaa());
        }
        while (B.koko() > 0) {
           C.lisaaLokero(B.poistaPaa());
        }
        
        tulostaminen("or", C);
     }
  
     // or-toiminto
     public void intersection(TiraTaulu htA, TiraTaulu htB){
        TiraTaulu A = htA;
        TiraTaulu B = htB;
        TiraTaulu C = new TiraTaulu();
        
        while ((A.koko() > 0) && (B.koko() > 0)) {
           int a = A.paa().node().data();
           int b = B.paa().node().data();
           
           if (a == b) {
              C.lisaaLokero(A.poistaPaa());
              B.poistaPaa();
           } 
           else if (a < b) {
              A.poistaPaa();
           } 
           else if (b < a) {
              B.poistaPaa();
           }
        }
        
        tulostaminen("and", C);
     }
  
     // xor-toiminto
     public void exclude(TiraTaulu htA, TiraTaulu htB){
        TiraTaulu A = htA;
        TiraTaulu B = htB;
        TiraTaulu C = new TiraTaulu();
        
        while ((A.koko() > 0) && (B.koko() > 0)) {
           int a = A.paa().node().data();
           int b = B.paa().node().data();
           
           if (a == b) {
              A.poistaPaa();
              B.poistaPaa();
           } 
           else if (a < b) {
              C.lisaaLokero(A.poistaPaa());
           } else if (b < a){
              C.lisaaLokero(B.poistaPaa());
           }
        }
        while (A.koko() > 0) {
           C.lisaaLokero(A.poistaPaa());
        }
        while(B.koko() > 0){
           C.lisaaLokero(B.poistaPaa());
        }
        
        tulostaminen("xor", C);
    }

    /*toiminto, listan ja tulostuksen viimeistelyyn */
    public void tulostaminen (String operation, TiraTaulu C) {
        boolean more = true;
        while (more) {
            System.out.print("\nPaina \"1\" jos haluat poistaa numeron ");
            System.out.println(operation + "-hajautustaulusta,");
            System.out.println("      \"0\" jos haluat jatkaa ilman numeron poistoa");
            Scanner ab = new Scanner(System.in);
            int input = ab.nextInt();
            
            if (input == 1) {
                delete(C);
            } 
            else if (input == 0) {
                more = false;
            } 
            else {
                System.out.println("Väärä syöte");
            }
        }
        
        // muodostetaan tulostettava taulukko toivottuun muotoon
        String[] result = new String[C.koko()];
        int secondaryValue = Integer.MAX_VALUE;
        Lokero tmp = C.paa();
        
        for (int i = 0; i < result.length; i++) {
            // täydennetään taulukon toinen kolumni toivotulla arvolla
            if (operation == "or") {
                secondaryValue = tmp.node().amount();
            } 
            else if (operation == "and") {
                secondaryValue = tmp.node().rivi();
            } 
            else if (operation == "xor") {
                secondaryValue = tmp.node().source();
            }
            
            result[i] = tmp.node().data() + "   " + secondaryValue;
            tmp = tmp.seuraava();
        }
        writeOutput(result, operation + ".txt");
        System.out.println("Toiminto \"" + operation + "\" onnistui, tuloksena joukko, joka ");
        System.out.println("sisältää " + result.length + " alkiota.");
    }

    /* operation which performs the deletion of values */
    public void delete (TiraTaulu table) {
        if (table.koko() > 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\nAnna arvo, jonka haluat poistaa: ");
            int input = sc.nextInt();
            
            if (table.poista(input)){
                System.out.println("Arvon poisto onnistui.");
            } 
            else {
                System.out.println("Arvoa ei löydetty.");
            }
            sc.close();
        } 
        else {
            System.out.println("Taulu on tyhjä, ei voida poistaa arvoa.");
        }
    }

	public int[] lueInput(String luettavaTiedosto) {
		String line;
        int[] palautettavaTaulukko = new int[0];
	 	try {
            BufferedReader br = new BufferedReader( new FileReader(luettavaTiedosto));
            int koko = 0;
            //luetaan ensin tiedosto läpi, ja talletetaan tiedoston koko
            while (br.readLine() != null) {
                koko++;
            }
            br.close();

            //luodaan väliaikainen taulukko tiedoston sisältö varten
            int[] sisalto = new int[koko];
            BufferedReader br2 = new BufferedReader(new FileReader(luettavaTiedosto));
            
            //luetaan tiedosto uudestaan läpi, tällä kertaa tallettaen tiedoston sisältö
            //sisalto -taulukkoon
            for (int i = 0; i < koko; i++) {
                line = br2.readLine().trim();
                String[] tmp = line.split("\n");
                sisalto[i] = Integer.parseInt(tmp[0]);
            }
            br2.close();
            //viitataan valiaikais taulukkoon, sisalto
            palautettavaTaulukko = sisalto;
	 	} 
		catch(IOException e) {
	    	System.out.println("Tiedostoa ei löytynyt.");
	 	}
        catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("Tiedosto \"" + luettavaTiedosto + "\" luettu onnistuneesti.");
        return palautettavaTaulukko;
    }
    
    /* operation which updates the result files */
   public void writeOutput(String[] output, String operation){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(operation));	
            for (int i = 0; i < output.length; i++){
                bw.write(output[i]);
                bw.newLine();
            }
            bw.close();
        } 
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("\nKirjoitetaan tiedostoa \"" + operation + "\".");
    }
   
    public static void main (String[] args) { 
        Tira2019 ht = new Tira2019();
        ht.run();

    }

}