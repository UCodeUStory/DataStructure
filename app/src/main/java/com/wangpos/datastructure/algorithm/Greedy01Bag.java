package com.wangpos.datastructure.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 贪心算法求解01 背包问题
 * <p>
 * 假设背包最多承载的重量是150 ，不考虑体积的情况
 * <p>
 * 现在有 重量 wi = [ 35,30,60,50,40,10,25] 7个物品
 * 价值 pi = [10,40,30,50,35,40,30]
 * <p>
 * <p>
 * 按价值贪婪算法
 * 总重量=130
 * 总价值=165
 * <p>
 * 按最轻贪婪算法
 * 总重量=140
 * 总价值=155
 *
 * 按价值密度贪婪算法
 * 总重量=150
 * 总价值=170
 *
 *
 * 最终都得不到最优解，最优解需要动态规划法来解决
 */


public class Greedy01Bag {

    public static void main(String[] args) {

        // 确定子问题

        // 贪婪策略有3种，
        // 第一种给句物品价值选择，每次都选择价值最高的物品

        int[] pi = {10, 40, 30, 50, 35, 40, 30};
        List<Thing> things = new ArrayList<>();
        things.add(new Thing(35, 10, 0));
        things.add(new Thing(30, 40, 0));
        things.add(new Thing(60, 30, 0));
        things.add(new Thing(50, 50, 0));
        things.add(new Thing(40, 35, 0));
        things.add(new Thing(10, 40, 0));
        things.add(new Thing(25, 30, 0));

        Bag bag = getMAX(things);
        System.out.println("按价值使用贪婪算法");
        System.out.println(Arrays.asList(bag.datas));

        System.out.println("总重量=" + bag.getTotalWeight());
        System.out.println("总价值=" + bag.getTotalValue());
    }


    private static Bag getMAX(List<Thing> things) {
        Bag bag = new Bag(150);
        bag.datas = new ArrayList<>();
        for (int i = 0; i < things.size(); i++) {
//            Thing thing = getMaxValueThing(things);
//            Thing thing = getMinWeightThing(things);
            Thing thing = getMaxValueDensity(things);
            if (thing != null && thing.status == 0) {
                if (bag.weight >= thing.weight) {
                    thing.status = 1;
                    bag.datas.add(thing);
                    bag.weight = bag.weight - thing.weight;
                } else {
                    thing.status = 2;
                }
            }
        }

        return bag;
    }

    // 第一种给句物品价值选择，每次都选择价值最高的物品
    private static Thing getMaxValueThing(List<Thing> things) {
        Thing maxThing = null;
        for (Thing thing : things) {
            if (thing.status == 0) {
                if (maxThing == null) {
                    maxThing = thing;
                } else if (maxThing.value < thing.value) {
                    maxThing = thing;
                }
            }
        }
        return maxThing;
    }

    // 第二种给句物品价值选择，每次都选择最轻物品
    private static Thing getMinWeightThing(List<Thing> things) {
        Thing maxThing = null;
        for (Thing thing : things) {
            if (thing.status == 0) {
                if (maxThing == null) {
                    maxThing = thing;
                } else if (maxThing.weight > thing.weight) {
                    maxThing = thing;
                }
            }
        }
        return maxThing;
    }

    // 第三种给句物品价值选择，每次都价值密度大的
    private static Thing getMaxValueDensity(List<Thing> things) {
        Thing maxThing = null;
        for (Thing thing : things) {
            if (thing.status == 0) {
                if (maxThing == null) {
                    maxThing = thing;
                } else if ((maxThing.value*1f / maxThing.weight) < (thing.value*1f / thing.weight)) {
                    maxThing = thing;
                }
            }
        }
        return maxThing;
    }


    static class Bag {
        public Bag(int weight) {
            this.weight = weight;
        }

        List<Thing> datas;
        int weight;

        public int getTotalWeight() {
            int totalW = 0;
            for (Thing data : datas) {
                totalW = totalW + data.weight;
            }
            return totalW;
        }

        public int getTotalValue() {

            int totalV = 0;
            for (Thing data : datas) {
                totalV = totalV + data.value;
            }
            return totalV;
        }
    }

    static class Thing {
        @Override
        public String toString() {
            return "Thing{" +
                    "weight=" + weight +
                    ", value=" + value +
                    ", status=" + status +
                    '}';
        }

        public Thing(int weiget, int value, int status) {
            this.weight = weiget;
            this.value = value;
            this.status = status;
        }

        int weight;
        int value;
        int status;// 0未选中，1选中，2不合适

    }

}


