package ca.mcgill.ecse321.artgallerysystem;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Some helper methods for creating tables and table rows used in
 * purchases, uploaded art pieces, and frequently used addresses.
 * @author Zhekai Jiang
 */
public class TableLayoutUtils {

    /**
     * Initialize a table row, set margins, and make it clickable.
     * @author Zhekai Jiang
     * @param context The context for the row.
     * @param onClickListener The listener for clicking events on the row.
     * @return An instance of the TableRow created.
     */
    public static TableRow initializeRow(Context context, View.OnClickListener onClickListener) {
        TableRow row = new TableRow(context);

        // Add some vertical space between items
        // -> https://stackoverflow.com/questions/4577644/programmatically-set-margin-for-tablerow
        TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        rowLayoutParams.setMargins(0, 20, 0, 20);
        row.setLayoutParams(rowLayoutParams);

        // clickable table row -> https://stackoverflow.com/questions/13184484/clickable-tablerow-on-android
        // highlight on click -> https://stackoverflow.com/questions/4075356/how-can-i-highlight-the-table-row-on-click
        row.setBackgroundResource(android.R.drawable.list_selector_background);
        row.setOnClickListener(onClickListener);

        return row;
    }

}
