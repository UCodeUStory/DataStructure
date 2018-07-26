### Retrofit2.0 源码分析

    

##### retrofit 一共就是17个类，24个注解
#### 使用步骤
1. 第一步根据网络API定义一个接口

        public interface UserApi {
            @GET("/api/columns/{user} ")
            Call<User> getAuthor(@Path("user") String user)
        }


2. 第二步创建一个Retroft对象，并设置域名地址

        public static final String API_URL = "https://zhuanlan.zhihu.com";

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
            
3. 第三步再用这个retrofit对象创建一个UserApi对象：

        UserApi api = retrofit.create(UserApi.class);
        
4. 第四步调用这个API

        Call<User> call = api.getAuthor("ustory");
        //异步使用enqueue /[enk'ju:]/
        call.enqueue(new Callback<ZhuanLanAuthor>() {
          @Override
          public void onResponse(Response<ZhuanLanAuthor> author) {
              System.out.println("name： " + author.getName());
          }
          @Override
          public void onFailure(Throwable t) {
          }
        });
        //同步使用 [ˈeksɪkju:t]

#### Retrofit 原理分析

1. Retrofit构造函数


