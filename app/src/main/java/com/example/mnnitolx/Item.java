package com.example.mnnitolx;

public class Item
{
    String item_image,item_name,item_price,item_discreption,item_user_id,item_status,item_sold,item_id;
    Item()
    {

    }
    Item(String item_id,String item_image,String item_name,String item_price,String item_discreption,String item_user_id,String item_status,String item_sold)
    {
        this.item_id=item_id;
        this.item_image=item_image;
        this.item_name=item_name;
        this.item_price=item_price;
        this.item_discreption=item_discreption;
        this.item_user_id=item_user_id;
        this.item_status=item_status;
        this.item_sold=item_sold;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_discreption() {
        return item_discreption;
    }

    public void setItem_discreption(String item_discreption) {
        this.item_discreption = item_discreption;
    }

    public String getItem_user_id() {
        return item_user_id;
    }

    public void setItem_user_id(String item_user_id) {
        this.item_user_id = item_user_id;
    }

    public String getItem_status() {
        return item_status;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    public String getItem_sold() {
        return item_sold;
    }

    public void setItem_sold(String item_sold) {
        this.item_sold = item_sold;
    }
}
