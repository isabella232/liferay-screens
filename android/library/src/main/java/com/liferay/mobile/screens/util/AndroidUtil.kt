/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.mobile.screens.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.model.DocumentLocalFile
import java.io.InputStream
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.os.Build.VERSION.SDK_INT
import android.text.Html
import android.text.Spanned
import com.google.android.material.snackbar.Snackbar
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import java.io.IOException

/**
 * @author Victor Oliveira
 */
class AndroidUtil {

	companion object {

		@JvmStatic
		@Suppress("DEPRECATION")
		fun fromHtml(html: String?): Spanned {
			return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
			} else {
				Html.fromHtml(html)
			}
		}

		@JvmStatic
		fun getFileNameFromPath(uriString: String): String {
			return getUriFromString(uriString).lastPathSegment ?: ""
		}

		@JvmStatic
		fun getUriFromString(uriString: String): Uri {
			return Uri.parse(uriString)
		}

		@JvmStatic
		fun isConnected(applicationContext: Context): Boolean {
			val connectivityManager = applicationContext.getSystemService(
				Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

			connectivityManager?.let {
				return it.activeNetworkInfo?.isConnected ?: false
			}

			return false
		}

		@JvmStatic
		fun openLocalFileInputStream(applicationContext: Context, documentLocalFile: DocumentLocalFile): InputStream {
			val fileUri = AndroidUtil.getUriFromString(documentLocalFile.toString())

			return applicationContext.contentResolver.openInputStream(fileUri)!!
		}

		@JvmStatic
		@JvmOverloads
		fun showCustomSnackbar(
			view: View, message: String,
			duration: Int, @ColorInt backgroundColor: Int, @ColorInt textColor: Int, @DrawableRes icon: Int? = null) {

			val snackbar = Snackbar.make(view, message, duration)
			snackbar.view.setBackgroundColor(backgroundColor)

			val textView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as? TextView

			textView?.let {
				textView.setTextColor(textColor)

				icon?.let {
					val drawable = ContextCompat.getDrawable(view.context, icon)

					drawable?.let {
						DrawableCompat.setTint(drawable, textColor)

						textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
						textView.compoundDrawablePadding =
							view.context.resources.getDimensionPixelOffset(R.dimen.field_padding)
					}
				}
			}

			snackbar.show()
		}

		@JvmStatic
		fun updateHintLayout(hintTextView: TextView?, field: Field<*>) {
			if (field.tip.isNullOrEmpty()) {
				hintTextView?.visibility = View.GONE
			} else {
				hintTextView?.text = field.tip
				hintTextView?.visibility = View.VISIBLE
			}
		}

		@JvmStatic
		fun updateLabelLayout(labelTextView: TextView?, field: Field<*>, context: Context) {
			if (field.isShowLabel) {
				labelTextView?.text = field.label
				labelTextView?.visibility = View.VISIBLE

				if (field.isRequired) {
					val requiredAlert = ThemeUtil.getRequiredSpannable(context)
					labelTextView?.append(requiredAlert)
				}
			} else {
				labelTextView?.visibility = View.GONE
			}
		}

		@JvmStatic
		fun updateViewState(view: View?, enabled: Boolean) {
			view?.isEnabled = enabled
		}

		// TODO Remove after implement OpenAPI services
		@Throws(IOException::class)
		fun assetJSONFile(filename: String, context: Context): String {
			val file = context.assets.open(filename)
			val formArray = ByteArray(file.available())
			file.read(formArray)
			file.close()

			return String(formArray)
		}
	}
}
