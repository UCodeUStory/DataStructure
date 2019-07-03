package com.wangpos.datastructure.android

import okhttp3.Interceptor

abstract class Intercept {

    open fun intercept(chain: Chain):Response?{
        return chain.procced()
    }
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

/**
 * 时间分发就是这样搞的
 */
class MyIntercept: Intercept() {
    override fun intercept(chain: Chain): Response? {
        return super.intercept(chain)
    }
}

fun main() {

    val intercepts = arrayListOf<Intercept>()

    intercepts.add(object: Intercept() {
        override fun intercept(chain: Chain): Response? {
            chain.request.url = "123"
            // 这里不会立即返回，需要等最后一个拦截器执行完，这是一个递归的操作，也可以直接 return null 或者想要的数据
            return super.intercept(chain)
        }

    })
    //添加很多拦截器
    val request = Request("hahahah")
    val chain = Chain(0, intercepts, request)
    chain.procced(0, intercepts, request)
}


