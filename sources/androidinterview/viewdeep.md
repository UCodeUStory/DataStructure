### 计算View的深度，主要考擦树的遍历，以及广度优先搜索


     fun maxDeep(view: View): Int {
            //当前的view已经是最底层view了，不能往下累加层数了，返回0，代表view下面只有0层了
            if (!(view is ViewGroup)) {
                return 0
            }
            val vp = view as ViewGroup
            //虽然是viewgroup，但是如果并没有任何子view，那么也已经是最底层view了，不能往下累加层数了，返回0，代表view下面只有0层了
            if (vp.childCount == 0) {
                return 0
            }
            //用来记录最大层数
            var max = 0
            //广度遍历view
            //由于vp拥有子view，所以下面还有一层，因为可以+1，来叠加一层，然后再递归几岁算它的子view的层数
            for (i in 0 until vp.childCount) {
                val deep = maxDeep(vp.getChildAt(i)) + 1
                if (deep > max) {
                    max = deep
                }
            }
            return max
        }