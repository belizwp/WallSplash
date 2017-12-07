package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WallpaperPreviewActivityTest {

    @Rule
    public ActivityTestRule<WallpaperPreviewActivity> activityRule = new ActivityTestRule<>(
            WallpaperPreviewActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    @Before
    public void intent() {
        // setup needed data for test
        Photo photo = new Photo();
        photo.setId("Dwu85P9SOIk");
        photo.getUser().setId("eUO1o53muso");
        photo.getUser().setName("James Example");
        photo.getUser().getProfileImage().setMedium("https://images.unsplash.com/profile-1441298102341-b7ba36fdc35c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64");
        photo.getUrls().setSmall("https://images.unsplash.com/photo-1452457807411-4979b707c5be?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=400&fit=max");
        photo.getUrls().setRegular("https://images.unsplash.com/photo-1452457807411-4979b707c5be?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=1080&fit=max");
        photo.getUrls().setFull("https://images.unsplash.com/photo-1452457807411-4979b707c5be?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy");
        photo.getLinks().setHtml("https://unsplash.com/photos/xCmvrpzctaQ");
        photo.getLinks().setDownload("https://unsplash.com/photos/xCmvrpzctaQ/download");

        Intent intent = new Intent();
        intent.putExtra(Photo.class.getSimpleName(), photo);
        intent.putExtra("IS_MY_PHOTO_LIST", false);

        activityRule.launchActivity(intent);

        SystemClock.sleep(1000);
    }

    @Test
    public void addToCollection() {
        onView((withId(R.id.btnOption))).perform(click());
        onView(withId(R.id.action_add_to_collection)).perform(click());
        onView(withText("My Favorite")).perform(click());
    }

    // TODO: test setWallpaper, share, viewOnUnsplash
    // finding the way to block intent chooser

}
