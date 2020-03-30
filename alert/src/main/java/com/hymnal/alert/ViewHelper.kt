package com.hymnal.alert

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import org.jetbrains.anko.*


fun Context.drawable(@DrawableRes id: Int): Drawable =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        getDrawable(id)
    } else {
        resources.getDrawable(id)
    }

fun Context.color(@ColorRes id: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(id)
    } else {
        resources.getColor(id)
    }


fun Context.createAlertDialog(
    title: String?,
    msg: String?,
    leftString: String?,
    rightString: String?,
    listener: ((Boolean) -> Unit)?
): AlertDialog {

    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val dialog = builder.create()
    dialog.show()

    val dm = this.resources.displayMetrics
    val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels

    val height = if ((width / 10) > 16 * 9 ) width / 10 else 16 * 9
    val view = this.verticalLayout {

        if (!title.isNullOrEmpty()) {
            textView {

                gravity = Gravity.CENTER
                background = context.drawable(R.drawable.dialog_line_btm)

                text = title

                textSize = 15f
                textColor = context.color(R.color.dialog_msg_text_block)
            }.lparams(width = matchParent, height = height) {

            }
        }
        if (!msg.isNullOrEmpty()) {
            textView {
                gravity = Gravity.CENTER
                if (!leftString.isNullOrEmpty() || !rightString.isNullOrEmpty()) {
                    background = context.drawable(R.drawable.dialog_line_btm)
                }
                text = msg
                textSize = 14f
                textColor = context.color(R.color.dialog_msg_text_block)
                setPadding(40, 0, 40, 0)
            }.lparams(width = matchParent, height = width / 5) {
            }
        }

        verticalLayout {
            orientation = LinearLayout.HORIZONTAL

            if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                textView {
                    background =
                        context.drawable(R.drawable.dialog_btn_left_bg_pressed)
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        listener?.invoke(false)
                        dialog.dismiss()
                    }

                    text = leftString
                    textSize = 14f
                    textColor = context.color(R.color.dialog_msg_text_block)
                }.lparams(width = matchParent, height = height, weight = 1f)

                view {
                    background = context.drawable(R.drawable.dialog_line_ver)
                }.lparams(width = 1, height = matchParent) {
                    gravity = Gravity.CENTER
                }
            }

            textView {
                gravity = Gravity.CENTER
                background =
                    if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                        context.drawable(R.drawable.dialog_btn_right_bg_pressed)
                    } else {
                        context.drawable(R.drawable.dialog_btn_bottom_bg_pressed)

                    }
                setOnClickListener {
                    listener?.invoke(true)
                    dialog.dismiss()
                }
                textSize = 14f
                textColor = context.color(R.color.base_dialog_text)
                text = rightString
            }.lparams(width = matchParent, height = height, weight = 1f)
        }
    }.apply {
        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        background = context.drawable(R.drawable.dialog_bg)
    }

    dialog.setContentView(view)

    return dialog
}

fun Context.createImageAlertDialog(
    type: Alert.Type,
    msg: String?,
    leftString: String?,
    rightString: String?,
    listener: ((Boolean) -> Unit)?
): AlertDialog {

    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val dialog = builder.create()
    dialog.show()

    val dm = this.resources.displayMetrics
    val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels
    val height = if ((width / 10) > 16 * 9 ) width / 10 else 16 * 9
    val view = this.verticalLayout {

        imageView {
            val id = when (type) {
                Alert.Type.SUCCESS -> R.drawable.success_in
                Alert.Type.ERROR -> R.drawable.error_in
                Alert.Type.WARNING -> R.drawable.sigh_in
                else -> R.drawable.error_in
            }
            setImageResource(id)
            scaleType = ImageView.ScaleType.CENTER
            (drawable as Animatable).start()
            padding = 20
        }.lparams(width = matchParent, height = wrapContent) {

        }

        if (!msg.isNullOrEmpty()) {
            textView {
                gravity = Gravity.CENTER
                if (!leftString.isNullOrEmpty() || !rightString.isNullOrEmpty()) {
                    background = context.drawable(R.drawable.dialog_line_btm)
                }
                text = msg
                textSize = 14f
                textColor = context.color(R.color.dialog_msg_text_block)
                setPadding(40, 0, 40, 0)
            }.lparams(width = matchParent, height = width / 5) {
            }
        }
        if (leftString.isNullOrEmpty() && rightString.isNullOrEmpty()) return@verticalLayout

        verticalLayout {
            orientation = LinearLayout.HORIZONTAL

            if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                textView {
                    background = context.drawable(R.drawable.dialog_btn_left_bg_pressed)
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        listener?.invoke(false)
                        dialog.dismiss()
                    }
                    textSize = 14f
                    textColor = context.color(R.color.dialog_msg_text_block)
                    text = leftString
                }.lparams(width = matchParent, height = height, weight = 1f)

                view {
                    background = context.drawable(R.drawable.dialog_line_ver)
                }.lparams(width = 1, height = matchParent) {
                    gravity = Gravity.CENTER
                }
            }

            textView {
                gravity = Gravity.CENTER
                background =
                    if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                        context.drawable(R.drawable.dialog_btn_right_bg_pressed)
                    } else {
                        context.drawable(R.drawable.dialog_btn_bottom_bg_pressed)

                    }
                setOnClickListener {
                    listener?.invoke(true)
                    dialog.dismiss()
                }

                text = rightString
                textSize = 14f
                textColor = context.color(R.color.base_dialog_text)
            }.lparams(width = matchParent, height = height, weight = 1f)
        }
    }.apply {
        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        background = context.drawable(R.drawable.dialog_bg)
    }

    dialog.setContentView(view)

    return dialog
}

fun Context.createProgressDialog(msg: String?): AlertDialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val dialog = builder.create()
    dialog.show()

    val dm = this.resources.displayMetrics
    val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels

    val view = this.verticalLayout {

        themedProgressBar(R.style.MyProgressBar) {

            padding = 20
        }.lparams(width = matchParent, height = wrapContent) {


        }

        if (!msg.isNullOrEmpty()) {
            textView {
                gravity = Gravity.CENTER
                text = msg
                textSize = 14f
                textColor = context.color(R.color.dialog_msg_text_block)
                setPadding(40, 0, 40, 0)
            }.lparams(width = matchParent, height = width / 5) {
            }
        }

    }.apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        background = context.drawable(R.drawable.dialog_bg)
    }

    dialog.setContentView(view)
    return dialog
}

fun DialogFragment.show(manager: FragmentManager, tag:String) {
    if (manager.findFragmentByTag(tag) != null) return
    this.show(manager, tag)
}
