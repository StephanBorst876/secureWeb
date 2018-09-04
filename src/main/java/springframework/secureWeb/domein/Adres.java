package springframework.secureWeb.domein;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Adres")
public class Adres implements Serializable{

    public enum AdresType {
        POSTADRES, FACTUURADRES, BEZORGADRES;

        public static AdresType toAdresType(String adrestype) {
            if (adrestype.toUpperCase().equals("POSTADRES")) {
                return AdresType.POSTADRES;
            } else if (adrestype.toUpperCase().equals("BEZORGADRES")) {
                return AdresType.BEZORGADRES;
            } else if (adrestype.toUpperCase().equals("FACTUURADRES")) {
                return AdresType.FACTUURADRES;
            } else {
                return null;
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Adres_Id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private AdresType adresType;

//    @NotBlank(message = "Straatnaam is verplicht!")
    private String straatnaam;

    private int huisnummer;
    private String toevoeging;
    private String postcode;
    private String woonplaats;
    @ManyToOne
    private Klant klant;

    public Adres() {

    }

    public Adres(AdresType adresType) {
        this.id = 0L;
        this.adresType = adresType;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public void sethuisnummer(int huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getStraatnaam() {
        return this.straatnaam;
    }

    public int getHuisnummer() {
        return this.huisnummer;
    }

    public String getToevoeging() {
        return this.toevoeging;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public String getWoonplaats() {
        return this.woonplaats;
    }

    public AdresType getAdresType() {
        return this.adresType;
    }

    public void setAdresType(AdresType adresType) {
        this.adresType = adresType;
    }

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

}
