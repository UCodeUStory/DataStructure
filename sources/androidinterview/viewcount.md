### 统计一个ViewGroup中包含的子View的个数（递归和非递归实现）



     //递归写法
     fun calculateViewCount(view:View):Int{
            if (!(view is ViewGroup)){
                return 0
            }
            //统计子View数量
            var count = view.childCount
    
            for (i in 0 until view.childCount){
                count += calculateViewCount(view.getChildAt(i))
            }
    
            return count
        }
        
        
        //非递归写法
      fun calculateViewCount2(view: View): Int {
             if (!(view is ViewGroup)) {
                 return 0
             }
     
             var count = 0
             val linkList = LinkedList<View>()
     
             linkList.add(view)
             while (!linkList.isEmpty()) {
                 val currentView = linkList.removeFirst()
     
                 if (currentView is ViewGroup){
                     count += currentView.childCount
                     for (i in 0 until  currentView.childCount){
                         linkList.add(currentView.getChildAt(i))
                     }
                 }
     
             }
     
             return count
         }   