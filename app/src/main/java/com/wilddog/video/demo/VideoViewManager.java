package com.wilddog.video.demo;

import com.wilddog.video.WilddogVideoView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chaihua on 16-11-25.
 */
public class VideoViewManager {
    private static VideoViewManager videoViewManager;

    private LinkedHashMap<Integer, WilddogVideoView> views = new LinkedHashMap<>();

    private Set<Integer> availableViewSet = new HashSet<>();

    private VideoViewManager() {

    }

    public static VideoViewManager getVideoViewManager() {
        if (videoViewManager == null) {
            videoViewManager = new VideoViewManager();
        }
        return videoViewManager;
    }


    public void setView(WilddogVideoView videoView) {
        int key = videoView.hashCode();
        views.put(key, videoView);
        availableViewSet.add(key);
    }

    public void setView(List<WilddogVideoView> videoViews) {
        for (int i = 0; i < videoViews.size(); i++) {
            setView(videoViews.get(i));
        }
    }

    public void returnView(WilddogVideoView videoView) {
        availableViewSet.add(videoView.hashCode());
    }

    public WilddogVideoView getView() {
        Iterator<Integer> iterator = views.keySet().iterator();
        while (iterator.hasNext()) {
            Integer viewKey = iterator.next();
            if (availableViewSet.contains(viewKey)) {
                availableViewSet.remove(viewKey);
                return views.get(viewKey);
            }
        }
        return null;
    }

    public int getAvailableViewNum() {
        return availableViewSet.size();
    }

    public void dispose() {
        Set<Map.Entry<Integer, WilddogVideoView>> entries = views.entrySet();
        Iterator<Map.Entry<Integer, WilddogVideoView>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, WilddogVideoView> videoViewEntry = iterator.next();
            WilddogVideoView value = videoViewEntry.getValue();
            if (value != null) {
                value.release();
                value = null;
            }
        }
        this.views.clear();
    }
}
