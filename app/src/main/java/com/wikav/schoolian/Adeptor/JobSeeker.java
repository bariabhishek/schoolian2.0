package com.wikav.schoolian.Adeptor;

public class JobSeeker {

       private int image;
      private   String name;
   private String rupee;
   private int star;

    public JobSeeker(int image, String name, String rupee, int star) {
        this.image = image;
        this.name = name;
        this.rupee = rupee;
        this.star = star;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRupee() {
        return rupee;
    }

    public void setRupee(String rupee) {
        this.rupee = rupee;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
