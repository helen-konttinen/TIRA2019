package Harjoitustyo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;

public class Print {
    public String[] lueInput (String luettavaTiedosto) {
        String rivi;
        int[] palautettavaTaulukko = new String[0];

        try {
            BufferedReader br = new BufferedReader(new FileReader(luettavaTiedosto));
            //otetaan talteen luettavan tiedoston koko
            int koko = 0;
            //luetaan tiedosto ja otetaan talteen koko
            while (br.readLine() != null) {
                koko++;
            }
            br.close();

            int[] sisalto = new int[koko];
            BufferedReader br2 = new BufferedReader(new FileReader(luettavaTiedosto));

            for (int i = 0; i < koko; i++) {
                rivi = br2.readLine();
                String[] tmp = line.split("\n");
                sisalto[i] = Integer.parseInt(tmp[0]);
            }
            br2.close();
            palautettavaTaulukko = sisalto;
	 	} 
		catch(IOException e) {
	    	System.out.println("Tiedostoa ei löytynyt.");;
	 	}
        catch(Exception e) {
            System.out.println(e);
        }
        return palautettavaTaulukko;
    }

    public void tulostaOutput() {
        int esim1=3;
        int	esim2=1;
        int[] tulostettava = lueInput("setA.txt");
        try {
		    BufferedWriter bw = new BufferedWriter(new FileWriter("outp.txt")); 		
            for (int i = 0; i < tulostettava.length; i++) {
                bw.write(tulostettava[i]);
                bw.newLine();
            }
		    bw.close();
        } 
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("Writing file...");
    }

    public static void main(String[] args) {
	    Print ht = new Print();	
        String A = "setA.txt";
		String[] q = ht.lueInput(A);
        for (int i = 0; i < q.length; i++) {
            System.out.println(q[i]);
        }
		ht.tulostaOutput();
	}
}