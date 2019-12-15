package com.nour.redchamber.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Senators {
        @SerializedName("Image")
        @Expose
        private String Image;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("party")
        @Expose
        private String party;

        @SerializedName("district")
        @Expose
        private String district;

        @SerializedName("phone")
        @Expose
        private String phone;

        @SerializedName("email")
        @Expose
        private String email;


        // Getter Methods

        public String getImage() {
            return Image;
        }

        public String getName() {
            return name;
        }

        public String getParty() {
            return party;
        }

        public String getDistrict() {
            return district;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        // Setter Methods

        public void setImage( String Image ) {
            this.Image = Image;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public void setParty( String party ) {
            this.party = party;
        }

        public void setDistrict( String district ) {
            this.district = district;
        }

        public void setPhone( String phone ) {
            this.phone = phone;
        }

        public void setEmail( String email ) {
            this.email = email;
        }
}
