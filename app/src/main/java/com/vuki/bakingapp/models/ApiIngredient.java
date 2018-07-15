package com.vuki.bakingapp.models;

import com.squareup.moshi.Json;

import java.io.Serializable;

/**
 * Created by mvukosav
 */
public class ApiIngredient implements Serializable {

    @Json( name = "quantity")
   int quantity;
    @Json( name = "measure")
    String measure;
    @Json( name = "ingredient")
    String ingredient;
}
