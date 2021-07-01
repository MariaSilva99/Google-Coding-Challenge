package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;

  VideoLibrary() {
    this.videos = new HashMap<>();
    try {
      Scanner scanner = new Scanner(getClass().getResourceAsStream("/videos.txt"));

      //File file = new File(this.getClass().getResource("/videos.txt").getFile());

      //Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
//    } catch (FileNotFoundException e) {
    } catch (Exception e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

/*  public List<String> showList()
  {
    ArrayList<String> list = new ArrayList<String>();
    videos.forEach((key, value) -> {
      list.add(key + value);
    });

//    List<String> videoList = new ArrayList<String>(videos.keySet());
    return list;
  }*/

  List<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideoById(String videoId) {
    return this.videos.get(videoId);
  }

  Video getVideoByTitle(String title) {
    return this.videos.get(title);
  }
}
