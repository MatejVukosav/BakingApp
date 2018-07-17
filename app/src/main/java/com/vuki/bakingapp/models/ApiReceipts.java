package com.vuki.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mvukosav
 */
public class ApiReceipts implements Serializable {

    @Json( name = "receipts")
    List<ApiReceipt> receipts;

    public List<ApiReceipt> getReceipts() {
        return receipts;
    }
}
