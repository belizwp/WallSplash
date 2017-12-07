package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CollectionPreviewActivityTest {

    @Rule
    public ActivityTestRule<CollectionPreviewActivity> activityRule = new ActivityTestRule<>(
            CollectionPreviewActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    @Test
    public void intent() {
        // setup needed data for test
        Collection collection = new Collection();
        collection.setId(206);
        collection.setTitle("Test");
        collection.setDescription("Test");
        collection.getUser().setId("eUO1o53muso");
        collection.getUser().setName("James Example");
        collection.getUser().getProfileImage().setMedium("https://images.unsplash.com/profile-1441298102341-b7ba36fdc35c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64");

        Intent intent = new Intent();
        intent.putExtra(Collection.class.getSimpleName(), collection);

        activityRule.launchActivity(intent);

        SystemClock.sleep(1000);
    }
}
