package de.berlin.hwr.basketistics.ViewModel;

public class SpieleListViewItem {
    String datum;
    String zeit;
    String heim;
    String punkte_heim;
    String gast;
    String punkte_gast;
    String ort;
    public SpieleListViewItem(String datum, String zeit, String heim, String punkte_heim, String gast, String punkte_gast, String ort){
       this.datum = datum;
       this.zeit = zeit;
       this.heim = heim;
       this.punkte_heim = punkte_heim;
       this.gast = gast;
       this.punkte_gast = punkte_gast;
       this.ort = ort;

    }
}
