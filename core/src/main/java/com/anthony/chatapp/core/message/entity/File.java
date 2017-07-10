package com.anthony.chatapp.core.message.entity;

/**
 * Created by chend on 2017/7/3.
 */
public class File extends Message {
    private String fileName;
    private long fileSize;
    private FileTypes fileType;

    private File(){}

    public File(String receiver) {
        super(receiver);
    }

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

    @Override
    protected void encode(byte[] data) {
        data[0] = Message.Type.FILE.value();
    }

    public enum FileTypes {
        VEDIO, IMAGE, AUDIO, NORMAL
    }
}
