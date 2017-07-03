package com.anthony.chatapp.core.message.entity;

/**
 * Created by chend on 2017/7/3.
 */
public class File {
    private String fileName;
    private long fileSize;
    private FileTypes fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTypes getFileType() {
        return fileType;
    }

    public void setFileType(FileTypes fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType=" + fileType +
                '}';
    }

    public enum FileTypes {
        VEDIO, IMAGE, AUDIO, NORMAL
    }
}
