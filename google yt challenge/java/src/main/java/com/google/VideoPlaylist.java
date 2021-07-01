package com.google;

import java.util.ArrayList;

/** A class used to represent a Playlist */
class VideoPlaylist {

    private String title;
    private ArrayList<Video> videos = new ArrayList<Video>();

    VideoPlaylist(String title)
    {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public boolean vidInPlaylist(String videoID) {
        if (this.videos.size() == 0) return false;
        for (Video v : this.videos)
        {
            System.out.println("hi");
            if (v.getVideoId() == videoID) return true;
        }
        return false;
    }

    public void addVideo(Video video) {
        this.videos.add(video);
    }
}
