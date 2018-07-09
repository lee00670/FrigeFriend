package com.example.jlee.frigefriend;

public class FridgeItem {
    private  int itemID;
    private String itemName;
    private int catID;
    private int catImg;
    private  int quantity;
    private String quantityUnit;
    private String expDate;

    public FridgeItem(int itemID, String itemName, int catID, int catImg, int quantity, String quantityUnit, String expDate) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.catID = catID;
        this.catImg = catImg;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.expDate = expDate;
    }

    public int getCatImg() {
        return catImg;
    }

    public void setCatImg(int catImg) {
        this.catImg = catImg;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        catID = catID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

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

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return "{\"itemID\" : " + itemID + ", " +
                "\"itemName\" : " + itemName + ", " +
                "\"CatID\" : " + catID + ", " +
                "\"catImg\" : " + catImg + ", " +
                "\"quantity\" : " + quantity + ", " +
                "\"quantityUnit\" : " + quantityUnit+", " +
                "\"expDate\" : " + expDate+ "}";
    }
    /*
    *  String string = "20100101";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(string, formatter);
                    Log.e("test", "date: "+date); // 2010-01-02
    *
    * */
}
