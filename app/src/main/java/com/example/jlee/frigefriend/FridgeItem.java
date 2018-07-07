package com.example.jlee.frigefriend;

public class FridgeItem {
    private  int itemID;
    private  int quantity;
    private String quantityUnit;
    private String expDate;

    public int getItemID(){
        return itemID;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getQuantityUnit(){
        return quantityUnit;
    }
    public String getExpDate(){
        return expDate;
    }

    @Override
    public String toString() {
        return "{\"itemID\" : " + itemID + ", \"quantity\" : " + quantity + ", \"quantityUnit\" : " + quantityUnit+", \"expDate\" : " + expDate+ "}";
    }
    /*
    *  String string = "20100101";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(string, formatter);
                    Log.e("test", "date: "+date); // 2010-01-02
    *
    * */
}
