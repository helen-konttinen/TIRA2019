package Harjoitustyo.Tietoluokat;

/* Hajautustaulukossa k�ytett�v� tietotyyppi */
   public class TiraTaulu {
      
      /*lisätään annettu tiedosto hajautustauluun */
      public void lisaaTaulukko (int[] taulukko, int sourcefile){
         
         for (int i = 0; i < taulukko.length; i++) {
            int apu = taulukko[i];
            Lokero input = new Lokero(apu, new Node(apu, sourcefile, i+1));
         
            if (koko == 0) {
               paa = hanta = valiaikainen;
            }
            else {
               for (Lokero valiaikainen = paa; valiaikainen.seuraava() != null; valiaikainen = valiaikainen.seuraava()) {
                  //jos kyseinen indeksi löytyy taulusta...
                  if (valiaikainen.indeksi() == input.indeksi()) {
                     //...kasvatetaan indeksiä kunnes löyetään vapaa lokero
                     input.indeksi(input.ineksi()+1);
                  }
               }
               //sijoitetaan alkio
               lisaaNode(input);
            }
            //kasvatetaan taulun kokoa
            koko++;
         }
      }
      
      /*lisäysoperaation toinen osa */
      public void lisaaNode (Lokero input) {

         //jos sijoitettava alkio on pienin
         //otetaan talteen pää -lokero ja asetetaan uudeksi
         //pää lokeroksi sijoitettava alkio. Lopuksi liitettän
         //entinen pää -lokero nykyiseen pää -lokeroon edellinen -viittauksella
         if (input.indeksi() < paa.indeksi()) {
            Lokero valiaikainen = paa;
            paa = input;
            input.seuraava(valiaikainen);
            valiaikainen.edellinen(paa);
            
         } 
         //kun sijoitettava alkio on suurin
         //otetaan talteen häntä -lokero väliaikaiseen muuttujaan.
         //asetetaan sijoitettava alkio uudeksi häntä -lokeroksi.
         //Lopuksi liitetään entinen häntä -lokero uuteen häntä -lokeroon.
         else if (input.indeksi() > hanta.indeksi()) { 
            Lokero valiaikainen = hanta;
            hanta = input;
            input.edellinen(valiaikainen);
            valiaikainen.seuraava(hanta);
            
         } 
         // muut tapaukset:
         //otetaan talteen pää -lokero ja siitä seuraava lokero.
         //käydään lokeroiden indeksit läpi
         //jos sijoitettavan alkion indeksi on suurempi kuin pään -indeksi ja pienempi tai yhtä
         //suuri kuin päästä seuraavan alkion indeksi...
         //pää -lokeron seuraavaksi lokeroksi asetetaan sijoitettava alkio
         //päästä seuraavan edelliseksi lokeroksi asetetaan sijoitettava alkio
         //sijoitettavan alkion edelliseksi lokeroksi asetetaan entinen pää-lokero.
         //sijoitettavan alkion seuraavaksi lokeroksi asetetaan entisestä pää-lokerosta seuraava lokero.
         else { 
            Lokero valiaikainen = paa;
            Lokero tempSeuraava = paa.seuraava();
            
            for (long i = 0; i < koko - 1; i++) {
               if (input.indeksi() > valiaikainen.indeksi() && input.indeksi() <= tempSeuraava.indeksi()) { 
                  valiaikainen.seuraava(input);
                  tempSeuraava.edellinen(input);
                  input.edellinen(valiaikainen);
                  input.seuraava(tempSeuraava);
               } 
               else {
                  //"siirrytään" lokero eteenpäin vertailussa
                  valiaikainen = valiaikainen.seuraava();
                  tempSeuraava = tempSeuraava.seuraava();
               }
            }
         }

         /*else {
            //tarkistetaan onko jo olemassa lokeroa kyseisellä indeksillä
            while (loyda(valiaikainen.indeksi())) {
               //kasvatetaan moduloa ja indeksiä yhdellä kunnes löydetään vapaa lokero
               valiaikainen.mod(valiaikainen.mod() +1);
               valiaikainen.indeksi(valiaikainen.indeksi() +1);
            }
            // sijoitetaan alkio lopulliselle paikalle
            sijoita(valiaikainen);
         }
         koko++;*/
      }
      
      /*joukko-operaatioille lisäysoperaatio*/
      public void lisaaLokero (Lokero item) {
         Lokero tmp = new Lokero(item.node().data(), item.node());
         boolean onSijoitettu = false;

         if (koko == 0) {
            paa = hanta = item;
            koko++;
         }
         else {
            for (Lokero p = paa; p != null; p = p.seuraava()) {
               if (tmp.node().data() == p.node().data()) {
                  int apu = p.node().amount();
                  p.node().amount(apu + 1);
                  onSijoitettu = true;
                  p = hanta;
               }
            }
            if (!onSijoitettu) {
               lisaaNode(item);
               koko++;
            }
         }
      }

      /*listan pienimmän alkion poistava operaatio*/
      public Lokero poistaPaa () {
         if (koko != 0) {
            Lokero tmp = new Lokero(paa.node().data(), paa.node());

            if (koko > 1) {
               paa = paa.seuraava();
               head.edellinen(null);
            }
            else {
               paa = hanta = null;
            }
            koko--;
            return tmp;
         }
         else {
            return null;
         }
      }

      /*alkion poistaminen linkitetystä listasta*/
      public boolean poista (int poistettava) {
         boolean onnistui = false;
         if (koko != 0) {
            //kun poistettava alkio on pienin
            if ((koko == 1) || paa.node().data() == poistettava) {
               poistaPaa();
               onnistui = true;
            }
            //kun poistettava alkio on suurin
            else if (hanta.node().data() == poistettava) {
               hanta = hanta.edellinen();
               hanta.seuraava(null);
               koko--;
               onnistui = true;
            }
            //muut tapaukset
            else {
               for (Lokero tmp = paa; tmp != null; tmp = tmp.seuraava()) {
                  if (tmp.node().data() == poistettava) {
                     tmp.edellinen().seuraava(tmp.seuraava());
                     tmp.seuraava().edellinen(tmp.edellinen());
                     koko--;
                     onnistui = true;
                     tmp = hanta;
                  }
               }
            }
         }
         return onnistui;
      }

      public void print() {
         for (Lokero tmp = paa; tmp != null; tmp = tmp.seuraava()) {
            System.out.print("Indeksi: " + tmp.indeksi() + ", data: " + tmp.node().data() + ", ");
            System.out.println("Rivi: " + tmp.node().rivi() + ", määrä: " + tmp.node.amount());
         }
         System.out.println("Listan koko: " + koko);
      }
      

      /* Rakentajat */
      public TiraTaulu () {
         koko = 0;
         paa = hanta = null;
      }

      /* Setterit */
      public void koko (int k) {
         koko = k;
      }

      public void paa (Lokero p) {
         paa = p;
      }

      public void hanta (Lokero h) {
         hanta = h;
      }
      
      /* Getterit */
      public int koko () {
         return koko;
      }

      public Lokero paa () {
         return paa;
      }

      public Lokero hanta () {
         return hanta;
      }
      
      private int koko;
      private Lokero paa;
      private Lokero hanta;
   }