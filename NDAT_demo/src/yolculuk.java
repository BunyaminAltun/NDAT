import java.sql.*;

public class yolculuk {
    private int id;
    private int comuId;
    private String yolcuAdi;
    private String aracSahibiAdi;
    private Timestamp girisSaati;
    private Timestamp cikisSaati;
    private String varisNoktasi;

    public yolculuk(int id, int comuId, String yolcuAdi, String aracSahibiAdi, Timestamp girisSaati,
            Timestamp cikisSaati, String varisNoktasi) {
        this.id = id;
        this.comuId = comuId;
        this.yolcuAdi = yolcuAdi;
        this.aracSahibiAdi = aracSahibiAdi;
        this.girisSaati = girisSaati;
        this.cikisSaati = cikisSaati;
        this.varisNoktasi = varisNoktasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComuId() {
        return comuId;
    }

    public void setComuId(int comuId) {
        this.comuId = comuId;
    }

    public String getYolcuAdi() {
        return yolcuAdi;
    }

    public void setYolcuAdi(String yolcuAdi) {
        this.yolcuAdi = yolcuAdi;
    }

    public String getAracSahibiAdi() {
        return aracSahibiAdi;
    }

    public void setAracSahibiAdi(String aracSahibiAdi) {
        this.aracSahibiAdi = aracSahibiAdi;
    }

    public Timestamp getGirisSaati() {
        return girisSaati;
    }

    public void setGirisSaati(Timestamp girisSaati) {
        this.girisSaati = girisSaati;
    }

    public Timestamp getCikisSaati() {
        return cikisSaati;
    }

    public void setCikisSaati(Timestamp cikisSaati) {
        this.cikisSaati = cikisSaati;
    }

    public String getVarisNoktasi() {
        return varisNoktasi;
    }

    public void setVarisNoktasi(String varisNoktasi) {
        this.varisNoktasi = varisNoktasi;
    }
    

}


/* 
CREATE TABLE yolculuk (
    id SERIAL PRIMARY KEY,
    comuid INT REFERENCES kullanici(comuid),
    yolcu_adi VARCHAR(100),
    arac_sahibi_adi VARCHAR(100),
    giris_saati TIMESTAMP,
    cikis_saati TIMESTAMP,
    varıs_noktası VARCHAR(100)
);
*/