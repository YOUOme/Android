package com.example.youome;

public class ItemTab2 {
    private String name, date, money;
    private int type;

    public ItemTab2(int type, String name, String date, String money) {
        this.name = name;
        this.date = date;
        this.money = money;
        this.type = type;
    }

    public String getName(){ return name; }
    public String getDate(){return date;}
    public String getMoney(){return money;}
    public int getType(){return type;}

    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setMoney(String money) { this.money = money; }
    public void setType(int t){this.type = t;}
}
