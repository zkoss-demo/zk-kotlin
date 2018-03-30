package zk.kotlin.test;

import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.zkoss.zats.junit.AutoEnvironment
import org.zkoss.zul.Label
import org.zkoss.zul.Textbox
import zk.kotlin.test.util.asComp

/**
 * example Zats Test case for myIndex.zul see https://www.zkoss.org/wiki/ZATS_Essentials/Getting_Started
 */
class MyIndexTest1 : BaseTest() {
    @Test
    fun testMyIndex() = testPage("/myIndex.zul")

    @Test
    fun testSafeIndex() = testPage("/mySafeIndex.zul")

    fun testPage(pageUri: String) {
        val desktopAgent = client.connect(pageUri)

        val nameInput = desktopAgent.query("#name")
        val submitButton = desktopAgent.query("#submit")
        val responseLabel = desktopAgent.query("#response")

        assertEquals("", nameInput.`as`(Textbox::class.java).value)
        nameInput.input("Tester");
        assertEquals("Tester", nameInput.`as`(Textbox::class.java).value)

        submitButton.click();
        assertEquals("Hello Tester!", responseLabel.`as`(Label::class.java).value);
    }
}