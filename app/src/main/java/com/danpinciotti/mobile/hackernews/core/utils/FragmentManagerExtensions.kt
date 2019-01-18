package com.danpinciotti.mobile.hackernews.core.utils

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Creates a fragment if it doesn't already exist in the fragmentManager, and then displays it
 *
 * @param tag               tag used to identify a fragment
 * @param addToBackStack    whether or not this fragment should be added to the backstack
 * @param fragmentCreator   lambda to be called if a new fragment needs to be created
 */
@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentManager.showFragment(tag: String,
                                                @IdRes fragmentContainer: Int? = null,
                                                addToBackStack: Boolean = false,
                                                fragmentCreator: () -> T): T? {
    when {
        findFragmentByTag(tag) == null -> {
            val fragment = fragmentCreator()
            when {
                fragment is DialogFragment -> (fragment as DialogFragment).show(this, tag)
                fragmentContainer != null  -> {
                    val transaction = beginTransaction().replace(fragmentContainer, fragment, tag)
                    if (addToBackStack) transaction.addToBackStack(tag)
                    transaction.commit()
                }
            }
            return fragment
        }
        addToBackStack                 -> {
            popBackStack(tag, 0)
        }
    }
    return findFragmentByTag(tag) as T?
}