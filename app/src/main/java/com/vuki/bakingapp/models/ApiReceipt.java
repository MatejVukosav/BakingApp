package com.vuki.bakingapp.models;

import android.os.Parcel;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mvukosav
 */
public class ApiReceipt implements Serializable {

    @Json(name = "id")
    int id;

    @Json(name = "name")
    String name;

    @Json(name = "ingredients")
    List<ApiIngredient> ingredients;

    @Json(name = "steps")
    List<ApiSteps> steps;

    @Json(name = "servings")
    int servings;

    @Json(name = "image")
    String image;

    protected ApiReceipt( Parcel in ) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();

        in.readList( new ArrayList(), ApiSteps.class.getClassLoader() );
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<ApiIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients( List<ApiIngredient> ingredients ) {
        this.ingredients = ingredients;
    }

    public List<ApiSteps> getSteps() {
        return steps;
    }

    public void setSteps( List<ApiSteps> steps ) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings( int servings ) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String image ) {
        this.image = image;
    }

}
