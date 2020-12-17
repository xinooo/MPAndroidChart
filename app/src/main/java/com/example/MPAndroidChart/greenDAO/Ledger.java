package com.example.MPAndroidChart.greenDAO;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Ledger {
    @Id(autoincrement = true)
    private Long id;

    @NonNull//text非空
    private String name;
    private int dollars;
    private String date;
    private String type;
    private String photo;
    @Generated(hash = 597900432)
    public Ledger(Long id, @NonNull String name, int dollars, String date,
            String type, String photo) {
        this.id = id;
        this.name = name;
        this.dollars = dollars;
        this.date = date;
        this.type = type;
        this.photo = photo;
    }
    @Generated(hash = 749187879)
    public Ledger() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDollars() {
        return this.dollars;
    }
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
