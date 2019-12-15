package com.nour.redchamber.services;

import com.nour.redchamber.models.Senators;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
        @GET("9thsenate/9thsenate.json")
        Call<List<Senators>> getSenators();
}
