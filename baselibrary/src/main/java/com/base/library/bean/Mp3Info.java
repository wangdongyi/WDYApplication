package com.base.library.bean;
//Created by 王东一 on 2016/11/16.


import android.os.Parcel;
import android.os.Parcelable;

public class Mp3Info implements Parcelable {
    private long id;
    private String title;
    private String artist;
    private String album;
    private long albumId;
    private long duration;
    private long size;
    private String url;
    private int isPlay;//0未播放1播放
    private int isCache;//0未缓存1缓存

    public Mp3Info() {

    }

    public Mp3Info(Parcel parcel) {
        id = parcel.readLong();
        title = parcel.readString();
        artist = parcel.readString();
        album = parcel.readString();
        albumId = parcel.readLong();
        duration = parcel.readLong();
        size = parcel.readLong();
        url = parcel.readString();
        isPlay = parcel.readInt();
        isCache = parcel.readInt();
    }

    public int getIsCache() {
        return isCache;
    }

    public void setIsCache(int isCache) {
        this.isCache = isCache;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsPlay() {
        return isPlay;
    }

    public void setIsPlay(int isPlay) {
        this.isPlay = isPlay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeLong(albumId);
        dest.writeLong(duration);
        dest.writeLong(size);
        dest.writeString(url);
        dest.writeInt(isPlay);
        dest.writeInt(isCache);
    }

    public static final Creator<Mp3Info> CREATOR = new Creator<Mp3Info>() {

        @Override
        public Mp3Info[] newArray(int size) {
            return new Mp3Info[size];
        }

        @Override
        public Mp3Info createFromParcel(Parcel source) {
            return new Mp3Info(source);
        }
    };


}

