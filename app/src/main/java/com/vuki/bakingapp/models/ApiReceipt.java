package com.vuki.bakingapp.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mvukosav
 */
public class ApiReceipt implements Serializable {

    @Json( name = "id")
    int id;

    @Json( name = "name")
    String name;

    @Json( name = "ingredients")
    List<ApiIngredient> ingredients;

    @Json( name = "steps")
    List<ApiSteps> steps;

    @Json( name = "servings")
    int servings;

    @Json( name = "image")
    String image;

}
