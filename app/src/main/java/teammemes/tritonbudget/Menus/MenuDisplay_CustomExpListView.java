package teammemes.tritonbudget.Menus;

import android.content.Context;
import android.widget.ExpandableListView;


public class MenuDisplay_CustomExpListView extends ExpandableListView {
    private final int CHILDSUB_WIDTH = 1100;
    private final int CHILDSUB_HEIGHT = 20000;
    public MenuDisplay_CustomExpListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(CHILDSUB_WIDTH, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(CHILDSUB_HEIGHT, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

