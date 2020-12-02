package com.expose.volley;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.io.Serializable;

/**
 * Created by vineesh.expose on 30-10-2017.
 */

class Model implements  Serializable{


   public Model( ) {

   }

   @SerializedName("name")
   private  String NAME;
   @SerializedName("url")
   private String URL;


   @SerializedName("large")
   private  String LARGE;

   @SerializedName("timestamp")
   private  String TIMESTAMP;


   public String getNAME() {
      return NAME;
   }

   public void setNAME(String NAME) {
      this.NAME = NAME;
   }

   public String getURL() {
      return URL;
   }

   public void setURL(String URL) {
      this.URL = URL;
   }

   public String getLARGE() {
      return LARGE;
   }

   public void setLARGE(String LARGE) {
      this.LARGE = LARGE;
   }

   public String getTIMESTAMP() {
      return TIMESTAMP;
   }

   public void setTIMESTAMP(String TIMESTAMP) {
      this.TIMESTAMP = TIMESTAMP;
   }
}
