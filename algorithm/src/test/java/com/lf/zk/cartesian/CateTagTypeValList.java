package com.lf.zk.cartesian;


import java.util.*;

public class CateTagTypeValList {
    private String cateLevel1Id;
    private String cateLevel3Id;
    private String cateLevel3Name;
    private Map<String,List<CateTagPopDO>> tagTypeValListMap;


    public void addNewTagTypeVal(CateTagPopDO cateTagPopDO){
        if(tagTypeValListMap ==null){
            tagTypeValListMap =new HashMap<String, List<CateTagPopDO>>();
        }

        if(tagTypeValListMap.get(cateTagPopDO.getEntitSubtype())==null){
            List<CateTagPopDO> list= new ArrayList<CateTagPopDO>();
            list.add(cateTagPopDO);
            tagTypeValListMap.put(cateTagPopDO.getEntitSubtype(),list);
        }else {
            tagTypeValListMap.get(cateTagPopDO.getEntitSubtype()).add(cateTagPopDO);
        }
    }

    public class TagValue {
        private String tagName;
        private Double pop;

        public String getTagName() {
            return tagName;
        }

        public Double getPop() {
            return pop;
        }

        public void setPop(Double pop) {
            this.pop = pop;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
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

    public String getCateLevel3Name() {
        return cateLevel3Name;
    }

    public void setCateLevel3Name(String cateLevel3Name) {
        this.cateLevel3Name = cateLevel3Name;
    }

    public Map<String, List<CateTagPopDO>> getTagTypeValListMap() {
        return tagTypeValListMap;
    }

    public void setTagTypeValListMap(Map<String, List<CateTagPopDO>> tagTypeValListMap) {
        this.tagTypeValListMap = tagTypeValListMap;
    }
}
