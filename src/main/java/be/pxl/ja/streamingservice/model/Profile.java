package be.pxl.ja.streamingservice.model;


import be.pxl.ja.streamingservice.exception.InvalidDateException;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Comparable<Profile> {
    private static final int MAX_RECENTLY_WATCHED_SIZE = 5;
    private static final int MAX_CURRENTLY_WATCHING_SIZE = 3;
    private String name;
    private LocalDate dateOfBirth;
    private String avatar;
    private List<Content> recentlyWatched = new ArrayList<>();
    private List<Content> currentlyWatching = new ArrayList<>();
    private List<Content> myList = new ArrayList<>();

    public Profile(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public Profile(String name) {
        this(name, "profile1");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new InvalidDateException(dateOfBirth, "date of birth", "No date of birth in future allowed.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDateTime.now());
    }

    public void startWatching(Content content) {
        currentlyWatching.remove(content);
        currentlyWatching.add(0, content);
    }

    public void finishWatching(Content content) {
        currentlyWatching.remove(content);
        recentlyWatched.add(0, content);
    }

    public List<Content> getRecentlyWatched() {
        return recentlyWatched;
    }

    public List<Content> getCurrentlyWatching() {
        return currentlyWatching;
    }

    public List<Content> getMyList() {
        return myList;
    }

    public void addToMyList(Content content) {
        if (!isInMyList(content)) {
            myList.add(content);
        }
    }

    public boolean isInMyList(Content content) {
        return myList.contains(content);
    }


    public boolean allowedToWatch(Content content) {
        return content.getMaturityRating().getMinimumAge() <= getAge();
    }

    public void removeFromMyList(Content content) {
        myList.remove(content);
    }

    @Override
    public int compareTo(Profile o) {
        return this.name.compareTo(o.name);
    }
}
