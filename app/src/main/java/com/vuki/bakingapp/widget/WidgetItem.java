package com.vuki.bakingapp.widget;

import com.vuki.bakingapp.models.ApiIngredient;

import java.util.List;

/**
 * Created by mvukosav
 */
public class WidgetItem {

    List<ApiIngredient> ingredients;

    public WidgetItem( List<ApiIngredient> ingredients ) {
        this.ingredients = ingredients;
    }

    public List<ApiIngredient> getIngredients() {
        return ingredients;
    }
}
