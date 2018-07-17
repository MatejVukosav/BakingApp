package com.vuki.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.io.Serializable;

/**
 * Created by mvukosav
 */
public class ApiIngredient implements Serializable {

    @Json(name = "quantity")
    float quantity;
    @Json(name = "measure")
    String measure;
    @Json(name = "ingredient")
    String ingredient;

    protected ApiIngredient( Parcel in ) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}
