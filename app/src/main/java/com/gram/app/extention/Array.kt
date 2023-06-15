package com.gram.app.extention

fun <T> Array<T>.map(onCreateGraph: (first: T) -> Unit) {
    val (first) = this
    onCreateGraph(first)
}

fun <T> Array<T>.map(onCreateGraph: (first: T, second: T) -> Unit) {
    val (first, second) = this
    onCreateGraph(first, second)
}

fun <T> Array<T>.map(onCreateGraph: (first: T, second: T, third: T) -> Unit) {
    val (first, second, third) = this
    onCreateGraph(first, second, third)
}
