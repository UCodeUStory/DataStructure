### Hash 冲突

1. Hash冲突是指 不同的key  计算出同样的HashCode，此时HashMap通过链表的形式，将冲突的元素

插入到链表头部，这个链表保存的是Entry<key,value>