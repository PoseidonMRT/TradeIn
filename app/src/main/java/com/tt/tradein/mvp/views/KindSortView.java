package com.tt.tradein.mvp.views;

import com.tt.tradein.mvp.models.KindSort;

import java.util.List;

/**
 * Created by tuozhaobing on 16-5-17.
 */
public interface KindSortView {
    void presenKindSorts(List<KindSort> kindSorts);
    void presenterKindsError(String string);
}
