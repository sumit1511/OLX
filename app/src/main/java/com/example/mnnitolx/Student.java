package com.example.mnnitolx;

public class Student
{
    String fullname,registration_no,contact,email,password,room_no,hostel,user_id;
    public Student()
    {

    }
    public Student(String user_id,String fullname,String registration_no,String contact,String email,String password,String room_no,String hostel)
    {
        this.user_id=user_id;
        this.fullname=fullname;
        this.registration_no=registration_no;
        this.contact = contact;
        this.email=email;
        this.password=password;
        this.room_no=room_no;
        this.hostel=hostel;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
