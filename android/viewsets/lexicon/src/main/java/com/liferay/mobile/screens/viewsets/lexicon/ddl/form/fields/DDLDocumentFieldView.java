/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.lexicon.ddl.form.fields;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;

/**
 * @author Victor Oliveira
 */
public class DDLDocumentFieldView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView {

    public DDLDocumentFieldView(Context context) {
        super(context);
    }

    public DDLDocumentFieldView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLDocumentFieldView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void refresh() {
        EditText editText = getTextEditText();
        DocumentField field = getField();

        setupFieldLayout();

        editText.setText(field.toFormattedString());

        if (field.isUploaded()) {
            setRightDrawable(editText, R.drawable.lexicon_icon_clip_success_tinted);
            progressBar.setVisibility(GONE);
            refreshLayout(true);
        } else if (field.isUploadFailed()) {
            setRightDrawable(editText, R.drawable.lexicon_icon_cancel_error_tinted);
            progressBar.setVisibility(GONE);
            refreshLayout(false);
        } else if (field.isUploading()) {
            setRightDrawable(editText, R.drawable.lexicon_icon_clip_white_background);
            progressBar.setVisibility(VISIBLE);
            refreshLayout(true);
        } else {
            setRightDrawable(editText, R.drawable.lexicon_icon_clip_tinted);
            progressBar.setVisibility(GONE);
            refreshLayout(true);
        }
    }

    @Override
    protected Dialog createOriginDialog() {
        Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

        LayoutInflater factory = LayoutInflater.from(activity);
        final View customDialogView = factory.inflate(R.layout.ddlfield_select_document_dialog_lexicon, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(customDialogView);

        setupDialogListeners(activity, customDialogView);

        return bottomSheetDialog;
    }

    @Override
    public void onPostValidation(boolean valid) {
        refreshLayout(valid);
    }

    private void refreshLayout(boolean valid) {
        String errorMessage = getField().getErrorMessage();
        FormViewUtil.setupBackground(getContext(), valid, textEditText);
        FormViewUtil.setupErrorView(valid, findViewById(R.id.error_view), errorMessage);
    }
}