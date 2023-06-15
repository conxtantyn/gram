package com.gram.core.component

import android.content.Context
import com.gram.core.Builder

abstract class ComponentBuilder<P, C : P> : Builder {

    abstract fun build(context: Context): C
}
