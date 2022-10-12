package com.recyclerview.Common;

public class UploadProductModel {

    private String PostId;
    private long postAt;
    private String PostedBy;
    private String title, description, brand, model, madeYear, runKm, price, contactPersonName, contactNumber, contactNumberTwo;
    private String image;

    public UploadProductModel() {
    }

    public UploadProductModel(String postId, long postAt, String postedBy, String title, String description, String brand, String model, String madeYear, String runKm, String price, String contactPersonName, String contactNumber, String contactNumberTwo, String image) {
        PostId = postId;
        this.postAt = postAt;
        PostedBy = postedBy;
        this.title = title;
        this.description = description;
        this.brand = brand;
        this.model = model;
        this.madeYear = madeYear;
        this.runKm = runKm;
        this.price = price;
        this.contactPersonName = contactPersonName;
        this.contactNumber = contactNumber;
        this.contactNumberTwo = contactNumberTwo;
        this.image = image;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public long getPostAt() {
        return postAt;
    }

    public void setPostAt(long postAt) {
        this.postAt = postAt;
    }

    public String getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(String postedBy) {
        PostedBy = postedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMadeYear() {
        return madeYear;
    }

    public void setMadeYear(String madeYear) {
        this.madeYear = madeYear;
    }

    public String getRunKm() {
        return runKm;
    }

    public void setRunKm(String runKm) {
        this.runKm = runKm;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumberTwo() {
        return contactNumberTwo;
    }

    public void setContactNumberTwo(String contactNumberTwo) {
        this.contactNumberTwo = contactNumberTwo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
