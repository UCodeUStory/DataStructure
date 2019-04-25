package com.wangpos.datastructure.algorithm

import android.support.annotation.CheckResult


/**
 * （1）英国人住在红色的房子里
（2）瑞典人养狗作为宠物
（3）丹麦人喝茶
（4）绿房子紧挨着白房子，在白房子的左边
（5）绿房子的主人喝咖啡
（6）抽 Pall Mall 牌香烟的人养鸟
（7）黄色房子里的人抽 Dunhill 牌香烟

（8）住在中间那个房子里的人喝牛奶
（9）挪威人住在第一个房子里面

（10）抽 Blends 牌香烟的人和养猫的人相邻
（11）养马的人和抽 Dunhill 牌香烟的人相邻
（12）抽 BlueMaster 牌香烟的人喝啤酒
（13）德国人抽 Prince 牌香烟
（14）挪威人和住在蓝房子的人相邻
（15）抽 Blends 牌香烟的人和喝矿泉水的人相邻
 */
val yellow = 1
val blue = 2
val red = 3
val green = 4
val white = 5

val water = 1
val tea = 2
val milk = 3
val coffee = 4
val beer = 5

val nuowei = 1
val danmai = 2
val yingguo = 3
val deguo = 4
val ruidian = 5

val cat = 1
val horse = 2
val bird = 3
val fish = 4
val dog = 5

val Dunhill = 1
val Blends = 2
val PallMall = 3
val Prince = 4
val BlueMaster = 5


var roomColors = arrayOf(1, 2, 3, 4, 5)
var drinks = arrayOf(1, 2, 3, 4, 5)
var countrys = arrayOf(1, 2, 3, 4, 5)
var pets = arrayOf(1, 2, 3, 4, 5)
var tobaccos = arrayOf(1, 2, 3, 4, 5)

var cnt: Long = 0

fun main() {

    var groups = arrayOf(Person(), Person(), Person(), Person(), Person())

    assignedColor(roomColors, groups)
}

/**
 * 分配颜色
 */
private fun assignedColor(
    roomColors: Array<Int>,
    groups: Array<Person>
) {
    for (i in roomColors.indices) {
        for (j in roomColors.indices) {
            if (i == j) continue
            for (k in roomColors.indices) {
                if (k == i || k == j) continue
                for (l in roomColors.indices) {
                    if (l == i || l == j || l == k) continue
                    for (m in roomColors.indices) {
                        if (m == i || m == j || m == k || m == l) continue

                        groups[0].room_color = roomColors[i]
                        groups[1].room_color = roomColors[j]
                        groups[2].room_color = roomColors[k]
                        groups[3].room_color = roomColors[l]
                        groups[4].room_color = roomColors[m]
                        // 剪枝操作
                        // 再分配国家
                        for (groupIdx in groups.indices) {
                            if (groupIdx + 1 < groups.size) {
                                if (groups[groupIdx].room_color === green
                                    && groups[groupIdx + 1].room_color == white
                                ) {
                                    assignedCountry(groups)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 分配国家
 */
fun assignedCountry(groups: Array<Person>) {
    /*应用规则(9)：挪威人住在第一个房子里面；*/
    groups[0].country = nuowei;

    for (i in countrys.indices) {
        for (j in countrys.indices) {
            if (i == j) continue
            for (k in countrys.indices) {
                if (k == i || k == j) continue
                for (l in countrys.indices) {
                    if (l == k || l == j || l == i) continue
                    for (m in countrys.indices) {
                        if (m == i || m == j || m == k || m == l) continue
                        groups[1].country = countrys[j]
                        groups[2].country = countrys[k]
                        groups[3].country = countrys[l]
                        groups[4].country = countrys[m]

                        assigndDrink(groups)
                    }
                }
            }
        }
    }
}

/**
 * 分配喝的
 */
fun assigndDrink(groups: Array<Person>) {
    /*应用规则(8)：住在中间那个房子里的人喝牛奶；*/
    groups[2].drink = milk
    for (i in drinks.indices) {
        for (j in drinks.indices) {
            if (i == j) continue
            for (k in drinks.indices) {
                if (k == i || k == j) continue
                for (l in drinks.indices) {
                    if (l == k || l == j || l == i) continue
                    for (m in drinks.indices) {
                        if (m == i || m == j || m == k || m == l) continue
                        groups[0].drink = drinks[i]
                        groups[1].drink = drinks[j]
                        groups[3].drink = drinks[l]
                        groups[4].drink = drinks[m]

                        assigndPet(groups)
                    }
                }
            }
        }
    }
}

/**
 * 分配宠物
 */
fun assigndPet(groups: Array<Person>) {

    for (i in pets.indices) {
        for (j in pets.indices) {
            if (i == j) continue
            for (k in pets.indices) {
                if (k == i || k == j) continue
                for (l in pets.indices) {
                    if (l == k || l == j || l == i) continue
                    for (m in pets.indices) {
                        if (m == i || m == j || m == k || m == l) continue
                        groups[0].pet = pets[i]
                        groups[1].pet = pets[j]
                        groups[2].pet = pets[k]
                        groups[3].pet = pets[l]
                        groups[4].pet = pets[m]

                        assignedTabacco(groups)
                    }
                }
            }
        }
    }
}

fun assignedTabacco(groups: Array<Person>) {
    for (i in tobaccos.indices) {
        for (j in tobaccos.indices) {
            if (i == j) continue
            for (k in tobaccos.indices) {
                if (k == i || k == j) continue
                for (l in tobaccos.indices) {
                    if (l == k || l == j || l == i) continue
                    for (m in tobaccos.indices) {
                        if (m == i || m == j || m == k || m == l) continue
                        groups[0].tobacco = tobaccos[i]
                        groups[1].tobacco = tobaccos[j]
                        groups[2].tobacco = tobaccos[k]
                        groups[3].tobacco = tobaccos[l]
                        groups[4].tobacco = tobaccos[m]
                        DoGroupsfinalCheck(groups)
                    }
                }
            }
        }
    }
}

/**
 *
 */


fun DoGroupsfinalCheck(groups: Array<Person>) {
//    cnt++
//    println(cnt)

    if (checkResult(groups)) {
        PrintAllGroupsResult(groups)
        println()
    }
}

/**
 *
 *
 *  * （1）英国人住在红色的房子里
（2）瑞典人养狗作为宠物
（3）丹麦人喝茶
（4）绿房子紧挨着白房子，在白房子的左边
（5）绿房子的主人喝咖啡
（6）抽 Pall Mall 牌香烟的人养鸟
（7）黄色房子里的人抽 Dunhill 牌香烟


（10）抽 Blends 牌香烟的人和养猫的人相邻
（11）养马的人和抽 Dunhill 牌香烟的人相邻
（12）抽 BlueMaster 牌香烟的人喝啤酒
（13）德国人抽 Prince 牌香烟
（14）挪威人和住在蓝房子的人相邻
（15）抽 Blends 牌香烟的人和喝矿泉水的人相邻

 */
fun checkResult(groups: Array<Person>): Boolean {

    for (i in groups.indices) {
        var it = groups[i]
        //(1）英国人住在红色的房子里
        if (it.room_color == red) {
            if (it.country != yingguo) {
                return false
            }
        }

        //（2）瑞典人养狗作为宠物
        if (it.country == ruidian) {
            if (it.pet != dog) {
                return false
            }
        }

        //（3）丹麦人喝茶
        if (it.country == danmai) {
            if (it.drink != tea) {
                return false
            }
        }

        //（5）绿房子的主人喝咖啡鸟
        if (it.room_color == green) {
            if (it.drink != coffee) {
                return false
            }
        }

        //（6）抽 Pall Mall 牌香烟的人养鸟
        if (it.pet == bird) {
            if (it.tobacco != PallMall) {
                return false
            }
        }

        //（7）黄色房子里的人抽 Dunhill 牌香烟
        if (it.room_color == yellow) {
            if (it.tobacco != Dunhill) {
                return false
            }
        }

        //（12）抽 BlueMaster 牌香烟的人喝啤酒
        if (it.tobacco == BlueMaster) {
            if (it.drink != beer) {
                return false
            }
        }

        //13 德国人抽 Prince 牌香烟
        if (it.country == deguo) {
            if (it.tobacco != Prince) {
                return false
            }
        }


        //（14）挪威人和住在蓝房子的人相邻
        if (it.country == nuowei) {

            var first = if ((i - 1) < 0) {
                false
            } else {
                groups[i - 1].room_color == blue
            }

            var second = if ((i + 1) >= groups.size) {
                false
            } else {
                groups[i + 1].room_color == blue
            }
            if (!first && !second) {
                return false
            }
        }

        // 15）抽 Blends 牌香烟的人和喝矿泉水的人相邻
        if (it.tobacco == Blends) {

            var first = if ((i - 1) < 0) {
                false
            } else {
                groups[i - 1].drink == water
            }

            var second = if ((i + 1) >= groups.size) {
                false
            } else {
                groups[i + 1].drink == water
            }
            if (!first && !second) {
                return false
            }
        }

        // （10）抽 Blends 牌香烟的人和养猫的人相邻
        if (it.tobacco == Blends) {
            var first = if ((i - 1) < 0) {
                false
            } else {
                groups[i - 1].pet == cat
            }

            var second = if ((i + 1) >= groups.size) {
                false
            } else {
                groups[i + 1].pet == cat
            }
            if (!first && !second) {
                return false
            }
        }

        //（11）养马的人和抽 Dunhill 牌香烟的人相邻
        if (it.tobacco == Dunhill) {
            var first = if ((i - 1) < 0) {
                false
            } else {
                groups[i - 1].pet == horse
            }

            var second = if ((i + 1) >= groups.size) {
                false
            } else {
                groups[i + 1].pet == horse
            }
            if (!first && !second) {
                return false
            }
        }

        //（4）绿房子紧挨着白房子，在白房子的左边
        if ((i + 1) < groups.size) {
            if (groups[i].room_color == green && groups[i + 1].room_color != white) {
                return false
            }
        }

    }

    return true

}

/**
 *
 *
国家           房子           宠物           饮料           香烟
挪威           黄色             猫         矿泉水        Dunhill
丹麦           蓝色             马             茶         Blends
英国           红色             鸟           牛奶       PallMall
德国           绿色             鱼           咖啡         Prince
瑞典           白色             狗           啤酒     BlueMaster


房间颜色：黄 国家：挪威 宠物：猫 饮料：水 抽烟：Dunhill
房间颜色：蓝 国家：丹麦 宠物：马 饮料：茶 抽烟：Blends
房间颜色：红 国家：英国 宠物：鸟 饮料：牛奶 抽烟：PallMall
房间颜色：绿 国家：德国 宠物：鱼 饮料：咖啡 抽烟：Prince
房间颜色：白 国家：瑞典 宠物：狗 饮料：啤酒 抽烟：BlueMaster
 */
fun PrintAllGroupsResult(groups: Array<Person>) {

    groups.forEach {
        print("房间颜色：${getColorName(it.room_color)} ")
        print("国家：${getCountryName(it.country)} ")
        print("宠物：${getPetName(it.pet)} ")
        print("饮料：${getDrinkName(it.drink)} ")
        println("抽烟：${getTabaccoName(it.tobacco)} ")
    }
}

fun getColorName(room_color: Int): String {
    return when (room_color) {
        1 -> "黄"
        2 -> "蓝"
        3 -> "红"
        4 -> "绿"
        5 -> "白"
        else -> {
            " "
        }
    }
}

fun getCountryName(room_color: Int): String {
    return when (room_color) {
        1 -> "挪威"
        2 -> "丹麦"
        3 -> "英国"
        4 -> "德国"
        5 -> "瑞典"
        else -> {
            " "
        }
    }
}

fun getPetName(room_color: Int): String {
    return when (room_color) {
        1 -> "猫"
        2 -> "马"
        3 -> "鸟"
        4 -> "鱼"
        5 -> "狗"
        else -> {
            " "
        }
    }
}

fun getDrinkName(room_color: Int): String {
    return when (room_color) {
        1 -> "水"
        2 -> "茶"
        3 -> "牛奶"
        4 -> "咖啡"
        5 -> "啤酒"
        else -> {
            " "
        }
    }
}


fun getTabaccoName(room_color: Int): String {
    return when (room_color) {
        1 -> "Dunhill"
        2 -> "Blends"
        3 -> "PallMall"
        4 -> "Prince"
        5 -> "BlueMaster"
        else -> {
            " "
        }
    }
}

/**
 * 房子颜色	国籍	饮料	宠物	烟
 */
class Person(
    var room_color: Int = -1,
    var country: Int = -1,
    var drink: Int = -1,
    var pet: Int = -1,
    var tobacco: Int = -1
)
