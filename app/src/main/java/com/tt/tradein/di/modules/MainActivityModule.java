package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.domain.RestApiAdapter;
import com.tt.tradein.domain.service.WeatherApiService;
import com.tt.tradein.mvp.presenter.HomeViewPresenter;
import com.tt.tradein.mvp.presenter.HomeViewPresenterImpl;
import com.tt.tradein.mvp.presenter.KindSortPresenter;
import com.tt.tradein.mvp.presenter.KindSortPresenterImpl;
import com.tt.tradein.mvp.presenter.NearByPresenter;
import com.tt.tradein.mvp.presenter.NearByPresenterImpl;
import com.tt.tradein.mvp.presenter.PersonCenterFragmentPresenter;
import com.tt.tradein.mvp.presenter.PersonCenterFragmentPresenterImpl;
import com.tt.tradein.mvp.views.HomeView;
import com.tt.tradein.mvp.views.KindSortView;
import com.tt.tradein.mvp.views.NearByView;
import com.tt.tradein.mvp.views.PersonCenterView;
import com.tt.tradein.ui.fragment.NearByFragment;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
@UserScope
@Module
public class MainActivityModule {
    private HomeView view;
    private PersonCenterView personCenterView;
    private NearByView nearByView;
    private KindSortView kindSortView;

    public MainActivityModule(HomeView view) {
        this.view = view;
    }

    public MainActivityModule(PersonCenterView personCenterView){
        this.personCenterView = personCenterView;
    }

    public MainActivityModule(NearByView nearByView){
        this.nearByView = nearByView;
    }

    public MainActivityModule(KindSortView kindSortView){
        this.kindSortView = kindSortView;
    }

    @Provides
    public KindSortView provideKindSortView(){
        return kindSortView;
    }

    @Provides
    public PersonCenterView prividePersonCenterView(){
        return personCenterView;
    }

    @Provides
    public HomeView provideView() {
        return view;
    }

    @Provides
    public NearByView provideNearByView(){
        return nearByView;
    }

    @Provides
    public Retrofit provideRestAdapter() {
        return RestApiAdapter.getInstance();
    }

    @Provides
    public WeatherApiService provideHomeApi(Retrofit restAdapter) {
        return restAdapter.create(WeatherApiService.class);
    }

    @Provides
    public HomeViewPresenter providePresenter(HomeView homeView, WeatherApiService weatherApiService) {
        return new HomeViewPresenterImpl(homeView,weatherApiService);
    }

    @Provides
    public PersonCenterFragmentPresenter prividePersonCenterPresenter(PersonCenterView personCenterView1){
        return new PersonCenterFragmentPresenterImpl(personCenterView1);
    }

    @Provides
    public NearByPresenter provideNearByPresenter(NearByView nearByView){
        return new NearByPresenterImpl(nearByView);
    }

    @Provides
    public KindSortPresenter provideKindSortPresenter(KindSortView kindSortView){
        return new KindSortPresenterImpl(kindSortView);
    }
}
