package com.lf.algorithm.cartesian;

import java.util.List;

/**
 * 热度排行
 */
public class HeatRankDO {
    //内容
    private String content;
    //热度值
    private Double popScore;
    private String cateLevel1Id;
    private String cateLevel3Id;
    private List<CateTagPopDO> cateTagPopList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPopScore() {
        return popScore;
    }

    public void setPopScore(Double popScore) {
        this.popScore = popScore;
    }

    public List<CateTagPopDO> getCateTagPopList() {
        return cateTagPopList;
    }

    public void setCateTagPopList(List<CateTagPopDO> cateTagPopList) {
        this.cateTagPopList = cateTagPopList;
    }

    public String getCateLevel1Id() {
        return cateLevel1Id;
    }

    public void setCateLevel1Id(String cateLevel1Id) {
        this.cateLevel1Id = cateLevel1Id;
    }

    public String getCateLevel3Id() {
        return cateLevel3Id;
    }

    public void setCateLevel3Id(String cateLevel3Id) {
        this.cateLevel3Id = cateLevel3Id;
    }
}
