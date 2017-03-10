package teammemes.tritonbudget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import teammemes.tritonbudget.Menus.Menu;
import teammemes.tritonbudget.db.MenuDataSource;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MenuLoadTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        MenuDataSource menudb = new MenuDataSource(appContext);
        List<Menu> menus = menudb.getMenusByLocation("Pines");
        for(Menu menu:menus)
            Log.i("MENU:",menu.toString());
        menudb.close();
        assertEquals("teammemes.tritonbudget", appContext.getPackageName());
    }
}
