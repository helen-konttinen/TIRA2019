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