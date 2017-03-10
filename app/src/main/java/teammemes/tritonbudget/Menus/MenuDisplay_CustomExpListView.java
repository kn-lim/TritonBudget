package teammemes.tritonbudget.Menus;

import android.content.Context;
import android.widget.ExpandableListView;


public class MenuDisplay_CustomExpListView extends ExpandableListView {
    public MenuDisplay_CustomExpListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

