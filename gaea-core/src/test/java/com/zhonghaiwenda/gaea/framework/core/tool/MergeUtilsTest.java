package com.zhonghaiwenda.gaea.framework.core.tool;

import com.zhonghaiwenda.gaea.framework.core.Mergeable;
import jdk.nashorn.internal.ir.BaseNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * @author gongxuanzhang
 **/

class MergeUtilsTest {


    private List<CanMerge> list = new ArrayList<>();
    private List<CantMerge> cantList = new ArrayList<>();
    private String[] names = new String[10];
    private int[] targetCounts = new int[10];
    Map<String, Integer> mergeCount = new HashMap<>();

    Map<String, Integer> aliasCount = new HashMap<>();

    {
        for (int i = 0; i < 10; i++) {
            String name = UUID.randomUUID().toString();
            String alias = UUID.randomUUID().toString();
            for (int i1 = 0; i1 < 10; i1++) {
                int count = new Random().nextInt(900000);
                list.add(new CanMerge(name, alias, count));
                cantList.add(new CantMerge(name, alias, count));
                names[i] = name;
                targetCounts[i1] += count;
                mergeCount.merge(name, count, Integer::sum);
                aliasCount.merge(alias,count,Integer::sum);
            }
        }
    }

    @Test
    public void mergeMap() {
        Map<String, CanMerge> mergeMap = MergeUtils.mergeCollectionToMap(list);
        validate(mergeMap);
    }

    @Test
    public void mergeOneNull() {
        List<CanMerge> candidateList = null;
        List<CanMerge> canMerges = MergeUtils.mergeCollection(list, candidateList);
        validate(canMerges);
    }

    @Test
    public void mergeNullAndNull() {
        List<CanMerge> candidateList1 = null;
        List<CanMerge> candidateList2 = null;
        List<CanMerge> canMerges = MergeUtils.mergeCollection(candidateList1, candidateList2);
        Assertions.assertTrue(canMerges.isEmpty());
    }

    @Test
    public void mergeCant() {
        List<CantMerge> cantMerges = MergeUtils.mergeCollection(cantList, (bean) -> bean.name, (bean1, bean2) -> {
            bean1.count += bean2.count;
            return bean1;
        });
        cantMerges.forEach((bean) -> {
            Integer count = mergeCount.get(bean.name);
            Assertions.assertEquals(bean.count, count);
        });
    }

    @Test
    public void mergeAlias(){
        List<CanMerge> canMerges = MergeUtils.mergeCollection(list, (bean) -> bean.alias);
        canMerges.forEach((bean)-> Assertions.assertEquals(aliasCount.get(bean.alias),bean.count));
    }

    private void validate(List<CanMerge> mergeList) {
        mergeList.forEach((bean) -> {
            Integer count = mergeCount.get(bean.name);
            Assertions.assertEquals(bean.count, count);
        });
    }


    private void validate(Map<String, CanMerge> mergeMap) {
        mergeMap.forEach((name, bean) -> Assertions.assertEquals(mergeCount.get(name), bean.count));
    }


    class CantMerge {
        String name;
        String alias;
        int count;

        public CantMerge(String name, String alias, int count) {
            this.name = name;
            this.alias = alias;
            this.count = count;
        }
    }

    class CanMerge implements Mergeable<CanMerge> {

        String name;
        String alias;
        int count;

        public CanMerge(String name, String alias, int count) {
            this.name = name;
            this.alias = alias;
            this.count = count;
        }

        @Override
        public CanMerge merge(CanMerge that) {
            this.count += that.count;
            return this;
        }

        @Override
        public String unique() {
            return this.name;
        }
    }

}
