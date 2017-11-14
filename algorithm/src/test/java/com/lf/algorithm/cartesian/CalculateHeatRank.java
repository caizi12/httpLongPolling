package com.lf.algorithm.cartesian;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 * 标签热度排行计算
 */
public class CalculateHeatRank {

    public static void main(String[] args) throws IOException {
        CalculateHeatRank calculateHeatRank = new CalculateHeatRank();
        List<CateTagPopDO> list = calculateHeatRank.readData();

        Map<String, CateTagTypeValList> cateProductMap = new HashMap<String, CateTagTypeValList>();
        CateTagTypeValList cateTagTypeValList = null;
        for (CateTagPopDO cateTagPopDO : list) {
            if (cateProductMap.containsKey(cateTagPopDO.getCateLevel3Id())) {
                cateTagTypeValList = cateProductMap.get(cateTagPopDO.getCateLevel3Id());
            } else {
                cateTagTypeValList = new CateTagTypeValList();
                cateTagTypeValList.setCateLevel1Id(cateTagPopDO.getCateLevel1Id());
                cateTagTypeValList.setCateLevel3Id(cateTagPopDO.getCateLevel3Id());
                cateTagTypeValList.setCateLevel3Name(cateTagPopDO.getCateLevel3Name());
            }
            cateTagTypeValList.addNewTagTypeVal(cateTagPopDO);

            cateProductMap.put(cateTagPopDO.getCateLevel3Id(), cateTagTypeValList);
        }

        System.out.println(new Gson().toJson(cateProductMap));

        calculateHeatRank.calcuateCartesian(cateProductMap);
    }

    /**
     * 计算笛卡尔集
     *
     * @param cateProductMap
     */
    private void calcuateCartesian(Map<String, CateTagTypeValList> cateProductMap) {
        List<HeatRankDO> resultList=new ArrayList<HeatRankDO>();
        //产品词标签类型循环
        for (CateTagTypeValList cateTagTypeValList : cateProductMap.values()) {
            resultList.addAll(generateCartesian(cateTagTypeValList));
        }

        for(HeatRankDO heatRank: resultList){
            System.out.println(heatRank.getPopScore()+"/"+heatRank.getContent());
        }


        Collections.sort(resultList, new Comparator<HeatRankDO>() {
            public int compare(HeatRankDO DO1, HeatRankDO DO2) {
                int result=0;
                if(DO2.getPopScore()-DO1.getPopScore()>0){
                    result=1;
                }else if(DO2.getPopScore()-DO1.getPopScore()<0){
                    result=-1;
                }
                return result;
            }
        });

        System.out.println();
    }

    /**
     * 单个产品词生成笛卡尔集
     */
    private List<HeatRankDO> generateCartesian(CateTagTypeValList cateTagTypeValList) {
        Iterator<List<CateTagPopDO>> tagTypeValIterator = cateTagTypeValList.getTagTypeValListMap().values().iterator();
        List<List<CateTagPopDO>> tagTypeList = new ArrayList<List<CateTagPopDO>>();
        while (tagTypeValIterator.hasNext()) {
            tagTypeList.add(tagTypeValIterator.next());
        }


        List<List<CateTagPopDO>> tempResultList = new ArrayList<List<CateTagPopDO>>();

        for(CateTagPopDO cateTagPopDO:tagTypeList.get(0)){
            List<CateTagPopDO> tempList=new ArrayList<CateTagPopDO>();
            tempList.add(cateTagPopDO);
            tempResultList.add(tempList);
        }

        for (int i=1;i<tagTypeList.size();i++) {
            tempResultList=build(tempResultList,tagTypeList.get(i));
        }

        return converListToRank(tempResultList);
    }

    private List<HeatRankDO> converListToRank(List<List<CateTagPopDO>> tempResultList){
        Double totalPopScore=0.0;
        List<HeatRankDO> rankList=new ArrayList<HeatRankDO>();
        HeatRankDO rankDO=null;
        StringBuilder content=null;
        for(List<CateTagPopDO> tempList:tempResultList){
            totalPopScore=0.0;
            content=new StringBuilder();
            rankDO=new HeatRankDO();
            for(CateTagPopDO cateTagPopDO:tempList){
                totalPopScore+=cateTagPopDO.getPop();
                content.append(cateTagPopDO.getEntitSubtype()+":");
                content.append(cateTagPopDO.getEntitName());
                content.append("+");
            }
            content.append(tempList.get(0).getCateLevel3Name());
            rankDO.setContent(content.toString());
            rankDO.setPopScore(totalPopScore/tempList.size());
            rankDO.setCateTagPopList(tempList);
            rankList.add(rankDO);
        }
        return rankList;
    }


    public List<List<CateTagPopDO>> build(List<List<CateTagPopDO>> resulList, List<CateTagPopDO> currentCateList) {
        List<List<CateTagPopDO>> newResultList = new ArrayList<List<CateTagPopDO>>();
        for (List<CateTagPopDO> tempList : resulList) {
            for (CateTagPopDO cateTagPopDO : currentCateList) {
                List<CateTagPopDO> newList = new ArrayList<CateTagPopDO>(tempList);
                newList.add(cateTagPopDO);
                newResultList.add(newList);
            }
        }
        return newResultList;
    }

    public List<CateTagPopDO> readData() throws IOException {
        String dataPath = "/Users/lfeng/software/odps-client/tag2.txt";
        File dataFile = new File(dataPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "utf-8"));
        String str = null;
        List<CateTagPopDO> list = new ArrayList<CateTagPopDO>();
        String[] catTagStrs = null;
        while ((str = bufferedReader.readLine()) != null) {
            if (str != null) {
                catTagStrs = str.split(",");
                CateTagPopDO cateTagPopDO = new CateTagPopDO();
                cateTagPopDO.setCateLevel1Id(catTagStrs[0]);
                cateTagPopDO.setCateLevel3Id(catTagStrs[1]);
                cateTagPopDO.setCateLevel3Name(catTagStrs[2]);
                cateTagPopDO.setEntitId(catTagStrs[3]);
                cateTagPopDO.setEntitType(catTagStrs[4]);
                cateTagPopDO.setEntitSubtype(catTagStrs[5]);
                cateTagPopDO.setEntitName(catTagStrs[6]);
                cateTagPopDO.setPop(Double.parseDouble(catTagStrs[7]));
                list.add(cateTagPopDO);
            }
        }

        System.out.println(list.size() + "");
        return list;
    }
}