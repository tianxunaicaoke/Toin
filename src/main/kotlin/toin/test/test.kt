//package toin.test
//
//internal inline fun <reified T : Any> get(): T {
//    val value = when (T::class.simpleName) {
//        "B" -> B()
//        "C" -> C()
//        else -> B()
//    }
//    return value as T
//}
//
internal class B {
    fun print() = "B :$this"
}

internal class C {
    fun print() = "C :$this"
}

internal class A(private val b: B, private val c: C) {
    fun print() = "A: $this get real " + b.print()+"    "+ c.print()
}
//
//fun main() {
//    val a = A(get(), get())
//    print(a.print())
//}