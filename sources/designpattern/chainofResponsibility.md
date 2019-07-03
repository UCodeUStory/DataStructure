### 责任链模式

责任链模式是对一个事件的处理方法,所有能对事件进行处理的对象按顺序形成一个链表.事件经过链表中每个处理对象轮流处理.如果有返回值.则返回也是顺着这条链表反向返回.这个链表是先进后出模式.


Android中的源码分析
    
    Android中的事件分发机制就是类似于责任链模式，关于事件分发机制
    
    根View 将事件传递给 子View  子View 再传递给子View  ；子View 开始处理，没出处理就返回上一级去处理，递归是回溯调用，是责任链实现的一种方式
    
    另外，OKhttp中对请求的处理也是用到了责任链模式，有兴趣的可以去看下OKhttp的源码。后面有时间也会对OKhttp的源码进行分析。


1. 定义责任链的抽象


    public interface Interceptor {
      Response intercept(Chain chain) throws IOException;
    
      interface Chain {
        Request request();
    
        Response proceed(Request request) throws IOException;
    
        /**
         * Returns the connection the request will be executed on. This is only available in the chains
         * of network interceptors; for application interceptors this is always null.
         */
        @Nullable Connection connection();
      }
    }
    
    
    
    
OkHttp 中责任链实现



            
            
            interface Intercept {
            
                fun intercept(chain: Chain):Response?
            }
            
            class Chain(val index: Int, val intercepts: List<Intercept>,val  request: Request) {
            
                fun procced(index: Int, intercepts: List<Intercept>, request: Request):Response? {
                    if (index < intercepts.size) {
                        val intercept = intercepts.get(index)
                        val next = Chain(index+1,intercepts,request)
                        val response = intercept.intercept(next)
                        return response
                    }
            
                    return null
                }
            
                fun procced():Response?{
                   return procced(index,intercepts,request)
                }
            }
            
            class Response
            class Request(var url:String)
            
            
                    
            fun main() {
            
                val intercepts = arrayListOf<Intercept>()
            
                intercepts.add(object:Intercept{
                    override fun intercept(chain: Chain): Response? {
                        chain.request.url = "123"
                        // 这里不会立即返回，需要等最后一个拦截器执行完，这是一个递归的操作，也可以直接 return null 或者想要的数据
                        return chain.procced()
                    }
            
                })
                //添加很多拦截器
                val request = Request("hahahah")
                val chain = Chain(0, intercepts, request)
                chain.procced(0, intercepts, request)
            }



            其中可以优化一下
            
            abstract class Intercept {
            
                open fun intercept(chain: Chain):Response?{
                    return chain.procced()
                }
            }
            
            class MyIntercept: Intercept() {
                override fun intercept(chain: Chain): Response? {
                    return super.intercept(chain)
                }
            }
            
            这种方式就可以在super前后做修改