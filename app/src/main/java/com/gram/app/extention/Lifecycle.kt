package com.gram.app.extention

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.findLifecycleOwner(): Fragment {
    var owner = parentFragment ?: this
    var fragment: Fragment? = this
    while (fragment?.parentFragment != null) {
        if (fragment.parentFragment is T) {
            owner = fragment.requireParentFragment()
        }
        fragment = fragment.parentFragment
    }
    return owner
}
