package zk.gradle.test;

import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.zkoss.zats.junit.AutoEnvironment
import org.zkoss.zul.Label
import org.zkoss.zul.Textbox
import zk.gradle.test.util.fromComp

/**
 * example Zats Test case for index.zul see https://www.zkoss.org/wiki/ZATS_Essentials/Getting_Started
 */
class IndexTest {
    companion object {
        //init/destroy a DefaultZatsEnvironment once for the whole test class
        @ClassRule @JvmField
        val env = AutoEnvironment("./src/main/webapp/WEB-INF", "./src/main/webapp")
    }

    //automatically creates/destroys a Zats Client around each @Test method
    @Rule @JvmField
    val client = env.autoClient()

    @Test
    fun testIndex() {
        val desktopAgent = client.connect("/index.zul")

        val nameInput = desktopAgent.query("#name")
        val submitButton = desktopAgent.query("#submit")
        val responseLabel = desktopAgent.query("#response")

        assertEquals("", nameInput.fromComp(Textbox::getValue))
        nameInput.input("Tester");
        assertEquals("Tester", nameInput.fromComp(Textbox::getValue))

        submitButton.click();
        assertEquals("Hello Tester!", responseLabel.fromComp(Label::getValue));
    }

    @Test
    fun testIndexSomeMore() {
        //will use a fresh Zats Client instance but share the same ZatsEnvironment
        //DesktopAgent desktopAgent = client.connect("/index.zul");
        //more tests here
    }
}