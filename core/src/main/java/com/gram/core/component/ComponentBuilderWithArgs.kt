package com.gram.core.component

import android.content.Context
import android.os.Parcelable
import com.gram.core.Builder

abstract class ComponentBuilderWithArgs<A : Parcelable, P, C : P> : Builder {

    abstract fun build(context: Context, args: A): C
}
