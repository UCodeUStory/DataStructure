### Android应用架构设计


整个项目分为三层，从下往上分别是：

1. Basic Component Layer: 基础组件层，顾名思义就是一些基础组件，包含了各种开源库以及和业务无关的各种自研工具库；
  (okhttp3,RxJava，Retrofit,RxAndroid,LeakCanary,Glide第三方的库)和（自研的一些库比如UIWidget,CommonUtil,InjectView,ActionLog）
  
  
2. Business Component Layer: 业务组件层，这一层的所有组件都是业务相关的;
（例如支付组件，推送组件(自己websocket实现的),登录组件等（可以给多个module用，比如开发两款产品））


3. Business Module Layer: 业务 Module 层，在 Android Studio 中每块业务对应一个单独的 Module。
（钱包模块，商城模块，动态模块，收发快递模块）