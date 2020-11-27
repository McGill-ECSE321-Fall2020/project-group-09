package ca.mcgill.ecse321.artgallerysystem;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Helper methods to construct simple vertical linear layouts.
 * Used for constructing linear layouts in dialogs in PurchaseDetail.
 * @author Zhekai Jiang
 */
public class LinearLayoutUtils {

    /**
     * Initialize a vertical linear layout.
     * @author Zhekai Jiang
     * @param context The context of the layout.
     * @return The instance of the layout.
     */
    public static LinearLayout initVerticalLinearLayout(Context context) {
        LinearLayout dialogLayout = new LinearLayout(context);
        LinearLayout.LayoutParams dialogLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setLayoutParams(dialogLayoutParams);
        return dialogLayout;
    }

    /**
     * Construct and add a TextView to a linear layout, with margin specified.
     * Text content could have an HTML format.
     * @author Zhekai Jiang
     * @param content The content shown in the TextView.
     * @param viewGroup The LinearLayout to contain the TextView.
     * @param context The context of the layout.
     * @param leftMargin The margin on the left.
     * @param rightMargin The margin on the right.
     * @param topMargin The margin on the top.
     * @param bottomMargin The margin on the bottom.
     * @return The TextView instance created.
     */
    public static TextView addTextViewWithMargins(String content, ViewGroup viewGroup, Context context,
                                                  int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        textView.setLayoutParams(textViewLayoutParams);
        textView.setText(Html.fromHtml(content));
        viewGroup.addView(textView);
        return textView;
    }

    /**
     * Construct and add a TextView to a linear layout.
     * The left and right margins will be 0, and the top and bottom margins will be 5.
     * Text content could have an HTML format.
     * @author Zhekai Jiang
     * @param content The content shown in the TextView.
     * @param viewGroup The LinearLayout to contain the TextView.
     * @param context The context of the layout.
     * @return The TextView instance created.
     */
    public static TextView addTextViewNarrowMargin(String content, ViewGroup viewGroup, Context context) {
        return addTextViewWithMargins(content, viewGroup, context, 0, 5, 0, 5);
    }

    /**
     * Construct and add a TextView to a linear layout, used in form in dialog.
     * Text content could have an HTML format.
     * @author Zhekai Jiang
     * @param content The content shown in the TextView.
     * @param viewGroup The LinearLayout to contain the TextView.
     * @param context The context of the layout.
     * @return The TextView instance created.
     */
    public static TextView addTextViewInDialogForm(String content, ViewGroup viewGroup, Context context) {
        return addTextViewWithMargins(content, viewGroup, context, 65, 50, 65, 0);
    }

    /**
     * Construct and add an EditText, a field to edit texts, to a linear layout, used in form in dialog.
     * @author Zhekai Jiang
     * @param defaultContent The default content shown in the field.
     * @param viewGroup The LinearLayout to contain the EditText.
     * @param context The context of the layout.
     * @return The EditText instance created.
     */
    public static EditText addEditTextInDialogForm(String defaultContent, ViewGroup viewGroup, Context context) {
        LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editTextLayoutParams.setMargins(50, 0, 50, 0);
        EditText editText = new EditText(context);
        editText.setLayoutParams(editTextLayoutParams);
        editText.setText(defaultContent);
        viewGroup.addView(editText);
        return editText;
    }

}
