### //返回一个在vg下面的一个View，id为方法的第二个参数

    
     public static View find(ViewGroup vg, int id){
     
     
     }
     
     可以使用的方法有:
     
     View -> getId() 返回一个int 的 id
     ViewGroup -> getChildCount() 返回一个int的孩子数量
     ViewGroup -> getChildAt(int index) 返回一个孩子，返回值为View。
     
     这个题目就可以说非常经典了，以往的树形结构的题目，我们都是做一个二叉树的处理，除了左就是右，但是这里我们每个ViewGroup都可能有多个孩子，每个孩子既可能是ViewGroup，也可能只是View(ViewGroup是View的子类)
     
    
     //返回一个在vg下面的一个View，id为方法的第二个参数
     public static View find(ViewGroup vg, int id){
         if(vg == null) return null;
         int size = vg.getChildCount();
         //循环遍历所有孩子
         for(int i = 0 ; i< size ;i++){
             View v = vg.getChildAt(i);
             //如果当前孩子的id相同，那么返回
             if(v.getId == id) return v;
             //如果当前孩子id不同，但是是一个ViewGroup，那么我们递归往下找
             if(v instance of ViewGroup){
                 //递归
                 View temp = find((ViewGroup)v,id);
                 //如果找到了，就返回temp，如果没有找到，继续当前的for循环
                 if(temp != null){
                     return temp;
                 }
             }
         }
         //到最后还没用找到，代表该ViewGroup vg 并不包含一个有该id的孩子，返回空
         return null;
     }

