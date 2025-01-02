package id.myryan.laundry.model;

public class ModelLayanan {
    String id, tipe, harga;

    // Method Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) { // Ubah Tipe menjadi tipe
        this.tipe = tipe;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
