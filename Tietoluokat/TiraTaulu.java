package Harjoitustyo.Tietoluokat;

/* Hajautustaulukossa k�ytett�v� tietotyyppi */
   public class TiraTaulu {
      
      /*lisätään annettu tiedosto hajautustauluun */
      public void lisaaTaulukko (int[] taulukko, int sourcefile){
         
         for (int i = 0; i < taulukko.length; i++) {
            Node valiaikainen = new Node(taulukko[i], sourcefile, i);
            lisaaNode(valiaikainen);
         }
      }
      
      /*lisäysoperaation toinen osa */
      public void lisaaNode (Node input) {
         int apu = Integer.valueOf(input.data().toString());
         Lokero valiaikainen = new Lokero(apu, input);
         
         if (koko == 0) {
            paa = hanta = valiaikainen;
         } 
         else {
            //tarkistetaan onko jo olemassa lokeroa kyseisellä indeksillä
            while (loyda(valiaikainen.indeksi())) {
               //kasvatetaan moduloa ja indeksiä yhdellä kunnes löydetään vapaa lokero
               valiaikainen.mod(valiaikainen.mod() +1);
               valiaikainen.indeksi(valiaikainen.indeksi() +1);
            }
            // sijoitetaan alkio lopulliselle paikalle
            sijoita(valiaikainen);
         }
         koko++;
      }
      
      /*lisäysoperaation sijoittava osa */
      public void sijoita (Lokero input) {
         
         //jos sijoitettava alkio on pienin
         if (input.indeksi() < paa.indeksi()) {
            //otetaan talteen pää -lokero ja asetetaan uudeksi
            //pää lokeroksi sijoitettava alkio. Lopuksi liitettän
            //entinen pää -lokero nykyiseen pää -lokeroon edellinen -viittauksella
            Lokero valiaikainen = paa;
            paa = input;
            input.seuraava(valiaikainen);
            valiaikainen.edellinen(paa);
            
         } 
         //kun sijoitettava alkio on suurin
         else if (input.indeksi() > hanta.indeksi()) { 
            //otetaan talteen häntä -lokero väliaikaiseen muuttujaan.
            //asetetaan sijoitettava alkio uudeksi häntä -lokeroksi.
            //Lopuksi liitetään entinen häntä -lokero uuteen häntä -lokeroon.
            Lokero valiaikainen = hanta;
            hanta = input;
            input.edellinen(valiaikainen);
            valiaikainen.seuraava(hanta);
            
         } 
         // muut tapaukset
         else { 
            //otetaan talteen pää -lokero ja siitä seuraava lokero.
            Lokero valiaikainen = paa;
            Lokero tempSeuraava = paa.seuraava();
            //käydään lokeroiden indeksit läpi
            for (long i = 0; i < koko - 1; i++) {
               //jos sijoitettavan alkion indeksi on suurempi kuin pään -indeksi ja pienempi tai yhtä
               //suuri kuin päästä seuraavan alkion indeksi...
               if (input.indeksi() > valiaikainen.indeksi() && input.indeksi() <= tempSeuraava.indeksi()) {
                  //pää -lokeron seuraavaksi lokeroksi asetetaan sijoitettava alkio
                  valiaikainen.seuraava(input);
                  //päästä seuraavan edelliseksi lokeroksi asetetaan sijoitettava alkio
                  tempSeuraava.edellinen(input);
                  //sijoitettavan alkion edelliseksi lokeroksi asetetaan entinen pää-lokero.
                  input.edellinen(valiaikainen);
                  //sijoitettavan alkion seuraavaksi lokeroksi asetetaan entisestä pää-lokerosta seuraava lokero.
                  input.seuraava(tempSeuraava);
               } 
               else {
                  //"siirrytään" lokero eteenpäin vertailussa
                  valiaikainen = valiaikainen.seuraava();
                  tempSeuraava = tempSeuraava.seuraava();
               }
            }
         }
      }
      
      /* apuoperaatio joka tarkistaa onko kyseistä indeksiä taulussa */
      public boolean loyda (int k) {
         for (Lokero valiaikainen = paa; valiaikainen.seuraava() != null; valiaikainen = valiaikainen.seuraava()) {
            //jos kyseinen indeksi löytyy taulusta palautetaan true
            if (valiaikainen.indeksi() == k) {
               return true;
            }
         }
         return false;
      }
      

      /* Rakentajat */
      public TiraTaulu () {
         koko = 0;
         paa = hanta = null;
      }

      /* Setterit */
      public void asetaKoko (int k) {
         koko = k;
      }
      
      /* Getterit */
      public int koko () {
         return koko;
      }
      
      private int koko;
      private Lokero paa;
      private Lokero hanta;
   }