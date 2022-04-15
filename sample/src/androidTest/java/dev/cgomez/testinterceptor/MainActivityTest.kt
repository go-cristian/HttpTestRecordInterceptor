package dev.cgomez.testinterceptor

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies.setIdlingResourceTimeout
import androidx.test.espresso.IdlingPolicies.setMasterPolicyTimeout
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.cgomez.sample.App
import dev.cgomez.sample.MainActivity
import dev.cgomez.sample.R
import dev.cgomez.testrecordinterceptor.TestFileSaverInterceptor
import okhttp3.OkHttpClient
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit.SECONDS

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  lateinit var scenario: ActivityScenario<MainActivity>

  @Before
  fun startup() {
    setMasterPolicyTimeout(10, SECONDS);
    setIdlingResourceTimeout(10, SECONDS);
  }

  @After
  fun cleanup() {
    scenario.close()
  }

  @Test
  fun testEvent() {
    val app = ApplicationProvider.getApplicationContext<App>()

    val path = app.cacheDir.absolutePath
    val client = OkHttpClient
      .Builder()
      .addInterceptor(TestFileSaverInterceptor(path))
      .build()
    app.okHttpClient = client
    val intent = Intent(app, MainActivity::class.java)
    scenario = launchActivity(intent)
    Thread.sleep(1000)
    onView(withId(R.id.text)).check(matches(not(withText(""))))
  }
}