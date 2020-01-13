package com.wangpos.datastructure.leetcode;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * 项目之间的依赖关系，用拓扑排序解决。这比较明显。
 *
 * 难点在于怎么理解“ 同一小组的项目，排序后在列表中彼此相邻 ”。
 *
 *
 * 这个题通过提示可以知道使用两层拓扑排序，但是坑还是挺多的。
 * 1.需要给单独的项目创建一个组
 * 2.需要通过项目ID找到关联的组，同时需要维护组本身的序列
 * 一开始想图省事，用一个group[]表示，后来发现这样有耦合问题。改用List维护组本身，用数组维护项目到组的映射关系，初始化List为m个，如果遇到单独的项目，
 * 则把当前List大小设置为组ID分配给这个项目。通过这个解决了项目ID和组ID耦合的问题。
 *
 */
public class LeetCode1203 {

    public static void main(String args[]) {
        int[] group = new int[]{-1, -1, 1, 0, 0, 1, 0, -1};
        int n = 8;
        int m = 2;
        List<List<Integer>> beforeItems = new ArrayList<>();
        //[[],[6],[5],[6],[3,6],[],[],[]]
        beforeItems.add(createItem());
        beforeItems.add(createItem());
        beforeItems.get(beforeItems.size() - 1).add(6);
        beforeItems.add(createItem());
        beforeItems.get(beforeItems.size() - 1).add(5);
        beforeItems.add(createItem());
        beforeItems.get(beforeItems.size() - 1).add(6);
        beforeItems.add(createItem());
        beforeItems.get(beforeItems.size() - 1).add(3);
        beforeItems.get(beforeItems.size() - 1).add(6);
        beforeItems.add(createItem());
        beforeItems.add(createItem());
        beforeItems.add(createItem());

        int[] result = new LeetCode1203().sortItems(8, 2, group, beforeItems);
        System.out.println(Arrays.toString(result));

    }

    @NonNull
    private static List<Integer> createItem() {
        return new ArrayList<Integer>();
    }


    List<Integer>queue = new LinkedList<>();


    //项目
    static class Item {
        //项目id
        int id;

        //初始化入度
        int inputCnt;
        //下一个项目
        List<Integer> nextItems = new ArrayList<>();

        Item(int id) {
            this.id = id;
        }
    }

    //组
    static class Group {
        //组id
        int id;

        //入度
        int inputCnt;

        List<Integer> items = new ArrayList<>();
        //下一个 组
        List<Group> nextGroups = new ArrayList<>();

        Group(int id) {
            this.id = id;
        }
    }

    /**
     * 使用邻接表的形式
     * @param n
     * @param m
     * @param group
     * @param beforeItems
     * @return
     */
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        //项目数组
        Item[] items = new Item[n];

        //用来保存已经绑定过item的group数组
        Group[] itemToGroup = new Group[n];

        //组
        List<Group> oriGroups = new ArrayList<>();

        //初始化组种类
        for (int j = 0; j < m; j++) {
            oriGroups.add(new Group(j));
        }

        //初始化项目
        for (int i = 0; i < n; i++) {
            items[i] = new Item(i);
        }

        /**
         * 遍历每个项目，所属组
         */
        for (int i = 0; i < group.length; i++) {
            int groupId = group[i];
            if (groupId == -1) {// 项目不属于任何组
                //创建一个新组
                Group temp = new Group(oriGroups.size());
                //保存到组列表
                oriGroups.add(temp);
                //组绑定这个项目,因为项目是按顺序的所以i就是这个项目
                temp.items.add(i);
                itemToGroup[i] = temp;
            } else {
                //根据组id 绑定项目
                oriGroups.get(groupId).items.add(i);
                itemToGroup[i] = oriGroups.get(groupId);
            }
        }

        for (int i = 0; i < beforeItems.size(); i++) {
            List<Integer> array = beforeItems.get(i);
            //初始化入度
            items[i].inputCnt = array.size();
            for (Integer itemId : array) {
                //每个项目的下一个项目
                items[itemId].nextItems.add(i);
                //获取绑定项目后的组
                Group beforeGroup = itemToGroup[itemId];
                //当前组
                Group curGroup = itemToGroup[i];
                if (beforeGroup != curGroup) {
                    //前一个组保存他的下一个组
                    beforeGroup.nextGroups.add(curGroup);
                    //当前组入度多1
                    curGroup.inputCnt++;
                }
            }
        }

        Queue<Group> groupQueue = new LinkedList<>();

        //找到入度为0的组添加到待遍历的队列中
        for (Group ele : oriGroups) {
            if (ele.inputCnt == 0) {
                groupQueue.offer(ele);
            }
        }

        if (groupQueue.isEmpty()) {
            return new int[0];
        }

        int[] result = new int[n];
        int resultIndex = 0;
        while (!groupQueue.isEmpty()) {
            int size = groupQueue.size();
            for (int i = 0; i < size; i++) {
                Group curGroup = groupQueue.poll();
                Queue<Integer> itemQueue = new LinkedList<>();
                if (curGroup.items.isEmpty()) {
                    continue;
                }

                //再进行item拓扑排序
                for (int temp : curGroup.items) {
                    if (items[temp].inputCnt == 0) {
                        itemQueue.offer(temp);
                    }
                }

                if (itemQueue.isEmpty()) {
                    return new int[0];
                }

                //
                while (!itemQueue.isEmpty()) {
                    int itemQueueSize = itemQueue.size();
                    for (int j = 0; j < itemQueueSize; j++) {
                        Integer itemId = itemQueue.poll();
                        //保存结果
                        result[resultIndex++] = itemId;
                        //遍历下一个
                        for (int nextItemId : items[itemId].nextItems) {
                            items[nextItemId].inputCnt--;
                            if (items[nextItemId].inputCnt == 0 && curGroup.items.contains(nextItemId)) {
                                itemQueue.offer(nextItemId);
                            }
                        }
                    }
                }

                //项目中存在环
                for (int itemId : curGroup.items) {
                    if (items[itemId].inputCnt > 0) {
                        return new int[0];
                    }
                }

                //遍历下一个组
                for (Group nextGroup : curGroup.nextGroups) {
                    nextGroup.inputCnt--;
                    if (nextGroup.inputCnt == 0) {
                        groupQueue.offer(nextGroup);
                    }
                }
            }
        }
       //组中存在环
        for (Group ele : oriGroups) {
            if (ele.inputCnt > 0) {
                return new int[0];
            }
        }

        for (int k = 0; k < items.length; k++) {
            if (items[k].inputCnt > 0) {
                return new int[0];
            }
        }

        return result;
    }


}
