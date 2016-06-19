package com.tt.tradein.domain.service;

import com.tt.tradein.mvp.models.WeatherResultBean;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 天气接口Api
 */
public interface WeatherApiService {
    /**
     * 查询天气
     */
    @GET("apistore/weatherservice/cityname")
    Observable<WeatherResultBean> queryWeather(@Header("apikey") String apikey, @Query("cityname") String cityname);
}
