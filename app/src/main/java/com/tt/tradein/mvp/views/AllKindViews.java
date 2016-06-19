package com.tt.tradein.mvp.views;

import com.tt.tradein.mvp.models.Kind;
import com.tt.tradein.mvp.models.SecondKind;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
public interface AllKindViews {
    public static final String TAG = "AllKindViews";
    void showKind(List<Kind> kinds);
    void showSecondKind(List<SecondKind> secondKinds);
    void loadFailed(String str);
}
