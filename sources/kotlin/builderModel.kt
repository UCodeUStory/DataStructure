
class Car (
        val model: String?,
        val year: Int
) {
    private constructor(builder: Builder) : this(builder.model, builder.year)

    class Builder {
        var model: String? = null
        var year: Int = -1

        fun build() = Car(this)
    }

    companion object {
        //核心 fun <T> T.apply(f: T.() -> Unit): T { f(); return this }
        //调用者本身扩展一个apply方法, apply 允许可以传递一个无参数的函数无返回值的函数，然后扩展到调用者身上,并在函数体调用这个函数，并放回自身
        // 为什么要Builder.() -> Unit扩展，因为这样里面就可以调用当前对象的其他方法，也就是能使用this
        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()


    }
}
// usage 常规写法
val car = Car.build({
    model = "aa"
    year = 2018
})
// usage 简化写，好多初学者看到kotlin项目会一脸懵，这是什么意思，其实就是如果只有一个闭包参数就可以省略(),直接写{}，
val car = Car.build{
    model = "aa"
    year = 2018
}