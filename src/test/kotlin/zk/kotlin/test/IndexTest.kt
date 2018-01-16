package zk.gradle.test;

import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.zkoss.zats.junit.AutoEnvironment
import org.zkoss.zul.Label
import org.zkoss.zul.Textbox
import zk.gradle.test.util.asComp
import zk.gradle.test.util.fromComp

/**
 * example Zats Test case for index.zul see https://www.zkoss.org/wiki/ZATS_Essentials/Getting_Started
 */
class IndexTest : BaseTest() {
    @Test
    fun testIndex() {
        val desktopAgent = client.connect("/index.zul")

        val nameInput = desktopAgent.query("#name")
        val submitButton = desktopAgent.query("#submit")
        val responseLabel = desktopAgent.query("#response")

        assertEquals("", nameInput.asComp<Textbox>().value)
        nameInput.input("Tester");
        assertEquals("Tester", nameInput.asComp<Textbox>().value)

        submitButton.click();
        assertEquals("Hello Tester!", responseLabel.asComp<Label>().value);
    }
}