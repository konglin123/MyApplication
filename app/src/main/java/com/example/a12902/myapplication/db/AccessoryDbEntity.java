package com.example.a12902.myapplication.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "accessory_table")
public class AccessoryDbEntity {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 附件类型：
     * 1、照片;
     * 2、语音;
     * 3、视频
     */
    private int accType;
    /**
     * 时长：毫秒值
     */
    private String sc1;

    /**
     * 时长：00:20
     */
    private String sc2;

    /**
     * 缩略图地址
     */
    private String thumbPath;

    /**
     * 原文件地址
     */
    private String sourcePath;
    /**
     * 拍摄时间
     */
    private String pssj;
    @Generated(hash = 1709971290)
    public AccessoryDbEntity(Long id, int accType, String sc1, String sc2,
            String thumbPath, String sourcePath, String pssj) {
        this.id = id;
        this.accType = accType;
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.thumbPath = thumbPath;
        this.sourcePath = sourcePath;
        this.pssj = pssj;
    }
    @Generated(hash = 1021996549)
    public AccessoryDbEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getAccType() {
        return this.accType;
    }
    public void setAccType(int accType) {
        this.accType = accType;
    }
    public String getSc1() {
        return this.sc1;
    }
    public void setSc1(String sc1) {
        this.sc1 = sc1;
    }
    public String getSc2() {
        return this.sc2;
    }
    public void setSc2(String sc2) {
        this.sc2 = sc2;
    }
    public String getThumbPath() {
        return this.thumbPath;
    }
    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
    public String getSourcePath() {
        return this.sourcePath;
    }
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
    public String getPssj() {
        return this.pssj;
    }
    public void setPssj(String pssj) {
        this.pssj = pssj;
    }
}
