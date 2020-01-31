package Harjoitustyo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.*;
import Harjoitustyo.Tietoluokat.*;

public class Tira2019 {
    /* the main loop which runs the coursework */
    public void run() {
        System.out.println("Coursework handles numbers as Integers. The hashfunction used is (+1),");
        System.out.println("which means that overflow is possible by trying to enter");
        System.out.println("Integer.MAX_VALUE twice in a row or a group of values that would");
        System.out.println("end up exceeding Integer.MAX_VALUE.\n");
        
        TiraTaulu htA = new TiraTaulu();
        int[] setA = lueInput("setA.txt");
        
        TiraTaulu htB = new TiraTaulu();
        int[] setB = lueInput("setB.txt");
        
        boolean run = true;
        Scanner sc = new Scanner(System.in);

        while (run) {
            System.out.println("\nUse commands \"or\", \"and\" and \"xor\" to perform actions.");
            System.out.println("You can exit the program by using \"exit\" -command.");
            String s = sc.nextLine();
            String command = s.substring(0, 1);
            
            if ((s.charAt(0) == 'e') && (s.charAt(1) == 'x') && (s.charAt(2) == 'i') && 
            (s.charAt(3) == 't') && (s.length() == 4)) {
                run = false;
            } 
            else {
                // import the data from txt-files incase user doesnt wish to exit.
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
                    System.out.println("incorrect input");
                }
            }
        }
        System.out.println("\nCoursework closed.");
    }

    public void union(TiraTaulu htA, TiraTaulu htB) {  // and-operation
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
        while (B.size() > 0) {
           C.lisaaLokero(B.poistaPaa());
        }
        
        tulostaminen("or", C);
     }
  
     public void intersection(TiraTaulu htA, TiraTaulu htB){ // or-operation
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
  
     public void exclude(TiraTaulu htA, TiraTaulu htB){  // xor-operation
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
        while(B.size() > 0){
           C.lisaaLokero(B.poistaPaa());
        }
        
        tulostaminen("xor", C);
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
                line = br2.readLine();
                String[] tmp = line.split("\n");
                sisalto[i] = Integer.parseInt(tmp[0]);
                System.out.println(sisalto[i]);
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
        return palautettavaTaulukko;
	}
    public void run() {
        //luodaan uusi taulu ja luodaan siihen taulukkoja
        TiraTaulu hajautustaulu = new TiraTaulu();   
        int[] setA = lueInput("setA.txt");
        hajautustaulu.lisaaTaulukko(setA, 1);
        int[] setB = lueInput("setB.txt");
        hajautustaulu.lisaaTaulukko(setB, 2);
        
    }
   
    public static void main (String[] args) { 
        Tira2019 ht = new Tira2019();
        ht.run();

    }

    /*public void and (TiraTaulu A, TiraTaulu B) {
        for (int i = 0; i < A.koko(); i++) {
            A.
            for (int j = 0; i < B.koko(); j++) {

            }
        }   
    } */

}