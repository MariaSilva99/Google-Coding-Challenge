package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private String playingID = "";
  private String oldID_pause = "";
  private boolean paused = false;
  private ArrayList<VideoPlaylist> playlists = new ArrayList<VideoPlaylist>();


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public ArrayList<String> showList() {
    ArrayList<String> list = new ArrayList<String>();

    for(Video v : videoLibrary.getVideos())
    {
      list.add(v.getTitle() + " (" + v.getVideoId() + ") " + "[" + String.join(" ", v.getTags()) + "]");
    }

    Collections.sort(list);

    return list;
  }

  public void showAllVideos() {
    //    List<String> videos = videoLibrary.showList();
    /*    for(int i = 0; i < list.size() - 1; i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(i).compareTo(list.get(j)) > 0) {
          String temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        }
      }
    }*/

    System.out.println("Here's a list of all available videos:");

    for(String s : showList()) {
      System.out.println(s);
    }

  }

  public void playVideo(String videoId) {
    if (videoLibrary.getVideoById(videoId) == null) System.out.println("Cannot play video: Video does not exist");
    else
    {
      if (playingID != "")
      {
        Video oldVid = videoLibrary.getVideoById(playingID);
        System.out.println("Stopping video: " + oldVid.getTitle());
        paused = false;
      }
      Video vid = videoLibrary.getVideoById(videoId);
      System.out.println("Playing video: " + vid.getTitle());
      playingID = videoId;
    }

  }

  public void stopVideo() {
    Video vid = videoLibrary.getVideoById(playingID);

    if (playingID != "")
    {
      System.out.println("Stopping video: " + vid.getTitle());
      playingID = "";
    }
    else System.out.println("Cannot stop video: No video is currently playing");

  }

  public void playRandomVideo() {
    ArrayList<String> ids = new ArrayList<String>();

    for(Video v : videoLibrary.getVideos())
    {
      ids.add(v.getVideoId());
    }

    if (ids.size() == 0) System.out.println("No videos available");
    else {
      Random randID = new Random();
      String id = ids.get(randID.nextInt(ids.size()));

      playVideo(id);
    }
  }

  public void pauseVideo() {
    Video vid = videoLibrary.getVideoById(playingID);

    if (playingID != "" && oldID_pause != playingID)
    {
      System.out.println("Pausing video: " + vid.getTitle());
      oldID_pause = playingID;
      paused = true;
    }
    else if (playingID != "" && oldID_pause == playingID) System.out.println("Video already paused: " + vid.getTitle());
    else System.out.println("Cannot pause video: No video is currently playing");

  }

  public void continueVideo() {
    Video vid = videoLibrary.getVideoById(playingID);

    if (oldID_pause == playingID && paused == true)
    {
      System.out.println("Continuing video: " + vid.getTitle());
      paused = false;
    }
    else if (playingID == "") System.out.println("Cannot continue video: No video is currently playing");
    else System.out.println("Cannot continue video: Video is not paused");

  }

  public void showPlaying() {
    ArrayList<String> list = showList();

    if (playingID == "") System.out.println("No video is currently playing");
    else {
     for (int i = 0; i < list.size(); i++) {
       if (list.get(i).contains(playingID) && paused == true) System.out.println("Currently playing: " + list.get(i) + " - PAUSED");
       else if (list.get(i).contains(playingID) && paused == false) System.out.println("Currently playing: " + list.get(i));
     }
    }

  }

  public void createPlaylist(String playlistName) {
    boolean flag = false;

    if (playlistName.contains(" ")) System.out.println("The playlist name should have no whitespaces");

    if (playlists.size() == 0)
    {
      VideoPlaylist pl = new VideoPlaylist(playlistName);
      playlists.add(pl);
      System.out.println("Successfully created new playlist: " + playlistName);
    }
    else {
      for (VideoPlaylist l : playlists) {
        if (l.getTitle().toLowerCase().equals(playlistName.toLowerCase())) {
          System.out.println("Cannot create playlist: A playlist with the same name already exists");
          flag = true;
          break;
        }
      }
      if (!flag)
      {
        VideoPlaylist pl = new VideoPlaylist(playlistName);
        playlists.add(pl);
        System.out.println("Successfully created new playlist: " + playlistName);
      }
    }

  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    boolean pl_found = false, vid_found = false, videoInPlaylist = false;

    for (int i = 0; i < playlists.size(); i++) {
      if (playlistName.toLowerCase().equals(playlists.get(i).getTitle().toLowerCase())) {
        pl_found = true;
        break;
      }
    }

    if (videoLibrary.getVideoById(videoId) != null) vid_found = true;
    VideoPlaylist checkPlaylist = new VideoPlaylist(playlistName.toLowerCase());
    if (checkPlaylist.vidInPlaylist(videoId)) videoInPlaylist = true;
    System.out.println(checkPlaylist.getTitle());
    System.out.println(videoInPlaylist);

    if (pl_found && vid_found && !videoInPlaylist)
    {
      VideoPlaylist addVid = new VideoPlaylist(playlistName);
      addVid.addVideo(videoLibrary.getVideoById(videoId));
      System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideoById(videoId).getTitle());
    }
    else if (!pl_found && !vid_found)
    {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }
    else if (pl_found && videoInPlaylist) System.out.println("Cannot add video to " + playlistName + ": Video already added");
    else if (pl_found && !vid_found) System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
  }

  public void showAllPlaylists() {
    if (playlists.size() == 0) System.out.println("No playlists exist yet");
    else {
      for (int i = 0; i < playlists.size() - 1; i++) {
        if (playlists.get(i).getTitle().toLowerCase().compareTo(playlists.get(i + 1).getTitle().toLowerCase()) > 0) {
          VideoPlaylist temp = playlists.get(i);
          playlists.set(i, playlists.get(i + 1));
          playlists.set(i + 1, temp);
        }
      }

    System.out.println("Showing all playlists:");

    for (VideoPlaylist l : playlists) {
      System.out.println(l.getTitle());
    }
  }
    }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    boolean pl_found = false;

    for (int i = 0; i < playlists.size(); i++) {
      if (playlistName.toLowerCase().equals(playlists.get(i).getTitle().toLowerCase())) {
        pl_found = true;
        playlists.remove(playlists.get(i));
        System.out.println("Deleted playlist: " + playlistName);
        break;
      }
    }

    if (!pl_found) System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");

  }

  public void searchVideos(String searchTerm) {
    ArrayList<String> vidList = showList();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> found = new ArrayList<String>();

    for (Video v : videoLibrary.getVideos())
    {
      title.add(v.getTitle());
      id.add(v.getVideoId());
    }

    Collections.sort(title);
    Collections.sort(id);

    for (int i = 0; i < title.size(); i++) {
      if (title.get(i).toLowerCase().contains(searchTerm.toLowerCase()))
      {
        found.add(i+1 + ") " + vidList.get(i));
      }
    }

    if (found.size() == 0) System.out.println("No search results for " + searchTerm);
    else {
      System.out.println("Here are the results for " + searchTerm + ":");
      for (String s : found) System.out.println(s);
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner scanner = new Scanner(System.in);
      String vid = scanner.next();

      try {
        int vidNo = Integer.parseInt(vid);
        if (vidNo > 0 && vidNo <= found.size()) playVideo(id.get(vidNo - 1));
      } catch (NumberFormatException e) {
      }
    }
  }

  public void searchVideosWithTag(String videoTag) {
    ArrayList<String> vidList = showList();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<List> tags = new ArrayList<List>();

    for (Video v : videoLibrary.getVideos())
    {
      title.add(v.getTitle());
      id.add(v.getVideoId());
    }

    Collections.sort(title);
    Collections.sort(id);

    for (int i = 0; i < id.size(); i++) {
      Video v = videoLibrary.getVideoById(id.get(i));
      tags.add(v.getTags());
    }




    for (String s : title) System.out.println(s);
    for (List s : tags) System.out.println(s);

/*    for (int i = 0; i < title.size(); i++) {
      if (title.get(i).toLowerCase().contains(searchTerm.toLowerCase()))
      {
        found.add(i+1 + ") " + vidList.get(i));
      }
    }

    if (found.size() == 0) System.out.println("No search results for " + searchTerm);
    else {
      System.out.println("Here are the results for " + searchTerm + ":");
      for (String s : found) System.out.println(s);
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner scanner = new Scanner(System.in);
      String vid = scanner.next();

      try {
        int vidNo = Integer.parseInt(vid);
        if (vidNo > 0 && vidNo <= found.size()) playVideo(id.get(vidNo - 1));
      } catch (NumberFormatException e) {
      }
    }*/

  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}