package com.progresstech.app.Model;

public class ModelData {
    String idp, nama, sku, gambar, harga, harga_pokok, qty="";
    Integer diskon;

    public ModelData() {
    }
    public ModelData(String idp, String nama, String sku, String gambar, String harga, Integer diskon, String harga_pokok, String qty) {
        this.idp = idp;
        this.nama = nama;
        this.sku = sku;
        this.gambar = gambar;
        this.harga = harga;
        this.diskon = diskon;
        this.harga_pokok = harga_pokok;
        this.qty = qty;
    }

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public String getNama() { return nama; }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar (String gambar) {
        this.gambar = gambar;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga (String harga) {
        this.harga = harga;
    }

    public Integer getDiskon() {
        return diskon;
    }

    public void setDiskon (Integer diskon) {
        this.diskon = diskon;
    }

    public String getHarga_Pokok() {
        return harga_pokok;
    }

    public void setHarga_pokok (String harga_pokok) {
        this.harga_pokok = harga_pokok;
    }

    public String getQty() {
        return qty;
    }

    public void setQty (String qty) {
        this.qty = qty;
    }

}

