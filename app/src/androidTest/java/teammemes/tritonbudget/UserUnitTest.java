package teammemes.tritonbudget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import teammemes.tritonbudget.db.UserDataSource;
import teammemes.tritonbudget.db.User;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class UserUnitTest {

    UserDataSource ds;

    @Before
    public void setup(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        ds = new UserDataSource(appContext);
    }

    @After
    public void cleanup(){
        ds.close();
    }

    @Test
    public void updateOrInsert() throws Exception {
        User user = new User("Andrew Li",600.0,1);

            ds.updateInsertUser(user);
            User cUser = ds.getUser(1);
            Log.i("USER",cUser.toString());
            assertEquals(cUser.toString(),600.0,cUser.getBalance(),0.01);
    }
}