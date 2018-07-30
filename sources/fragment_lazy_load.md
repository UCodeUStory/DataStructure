### Fragment 懒加载


1.在onCreate方法中判断getUserVisibleHint()判断当前是否显示，然后进行拉数据更细

2. 如果不更新UI可以把拉去数据逻辑写在setUserVisibleHint(boolean isVisibleToUser)方法li
因为这个方法早与onCreate，所以如果数据秒回更新Ui会有问题
