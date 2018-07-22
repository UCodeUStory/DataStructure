### Android应用架构设计

没有最好的组件，只有更适合业务场景的组件

整个项目分为三层，从下往上分别是：

1. Basic Component Layer: 基础组件层，顾名思义就是一些基础组件，包含了各种开源库以及和业务无关的各种自研工具库；
  (okhttp3,RxJava，Retrofit,RxAndroid,LeakCanary,路由组件，Glide，第三方的库)和（自研的一些库比如UIWidget,CommonUtil,InjectView,ActionLog）
  
  
2. Business Component Layer: 业务组件层（这一层一个有可以没有，大型项目肯定会有），这一层的所有组件都是业务相关的;
（例如支付组件，推送组件(自己websocket实现的),公共登录组件等（可以给多个module用，比如开发两款产品）,H5组件，视频组件，文件组件）


3. Business Module Layer: 业务 Module 层，在 Android Studio 中每块业务对应一个单独的 Module。
（钱包模块，商城模块，动态模块，收发快递模块）


4. App壳将各个组件组装协作



然后每个模块使用MVP模式


也可以采用插件化