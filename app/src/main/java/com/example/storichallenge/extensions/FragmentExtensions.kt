package com.example.storichallenge.extensions

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.storichallenge.R
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.constants.StoriConstants.EMPTY_STRING
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewBinding(initialize: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        init {
            this@viewBinding
                .viewLifecycleOwnerLiveData
                .observe(
                    this@viewBinding
                ) { owner ->
                    owner.lifecycle.addObserver(this)
                }
        }


        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            binding = null
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return this.binding
                ?: if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    error("Called")
                } else {
                    initialize().also {
                        this.binding = it
                    }
                }
        }


    }

class AutoClearedValue<T : Any>(val fragment: Fragment) : ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object: DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _value = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "should never call auto-cleared-value get when it might not be available"
        )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)

fun Fragment.navigateTo(navigationAction: NavigationAction) {
    findNavController().navigate(
        navigationAction.actionId,
        navigationAction.arguments,
        navigationAction.navOptions
    )
}

fun Fragment.setUpFragmentHomeToolBar(toolbar: Toolbar, title: String) {
    toolbar.findViewById<TextView>(R.id.toolbar_title).apply {
        text = title
    }
    (requireActivity() as AppCompatActivity).apply {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}

fun Fragment.setUpFragmentToolBar(toolbar: Toolbar, title: String = EMPTY_STRING, withCloseButton: Boolean = false, withBackButton: Boolean = true) {
    toolbar.findViewById<TextView>(R.id.toolbar_title).apply {
        text = title
    }
    (requireActivity() as AppCompatActivity).apply {
        setSupportActionBar(toolbar)
        if (withBackButton) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_action_back)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (withCloseButton) {
            supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(requireContext(), R.drawable.icon_close))
        }
    }
}

fun Fragment.setOptionsMenu(@MenuRes menuRes: Int? = null, menuHomeCallback: (() -> Unit?)? = null, menuItemCallback: (() -> Unit?)? = null) {
    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            if (menuRes != null) {
                menuInflater.inflate(menuRes, menu)
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                android.R.id.home -> if (menuHomeCallback == null) {
                    this@setOptionsMenu.findNavController().popBackStack()
                } else {
                    menuHomeCallback.invoke()
                }
                else -> menuItemCallback?.invoke()
            }
            return true
        }

    }, viewLifecycleOwner)
}