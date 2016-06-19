package com.tt.tradein.mvp.views;

import java.util.List;

/**
 * Created by tuozhaobing on 16-5-12.
 */
public interface PublishView {
    void publishSuccess();
    void publishError(String str);
    void uploadSuccess(List<String> images);
    void uploadFailed(String str);
}
