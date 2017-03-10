package teammemes.tritonbudget.Menus;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ExpandableListView;


public class MenuDisplay_CustomExpListView extends ExpandableListView {
    private final int CHILDSUB_HEIGHT = 20000;
    Context ctx;
    public MenuDisplay_CustomExpListView(Context context) {
        super(context);
        this.ctx = context;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        WindowManager wm = (WindowManager) ctx.getSystemService(
                Context.WINDOW_SERVICE);
        Display display1 = wm.getDefaultDisplay();
        Point size1 = new Point();

        int width1;
        int height1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            display1.getSize(size1);
            width1 = size1.x;
            height1 = size1.y;

        } else {
            width1 = display1.getWidth();
            height1 = display1.getHeight();
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width1-50, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(CHILDSUB_HEIGHT, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

