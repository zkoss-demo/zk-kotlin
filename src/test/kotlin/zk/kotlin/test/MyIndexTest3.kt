package zk.kotlin.test;

import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.zkoss.zats.junit.AutoEnvironment
import org.zkoss.zats.mimic.ComponentAgent
import org.zkoss.zats.mimic.QueryAgent
import org.zkoss.zul.Label
import org.zkoss.zul.Textbox
import zk.kotlin.test.util.asComp

/**
 * example Zats Test case for myIndex.zul see https://www.zkoss.org/wiki/ZATS_Essentials/Getting_Started
 */
class MyIndexTest3 : BaseTest() {
    @Test
    fun testMyIndex() = testPage("/myIndex.zul")

    @Test
    fun testSafeIndex() = testPage("/mySafeIndex.zul")

    private val QueryAgent.nameInput get() = query("#name")
    private val QueryAgent.submitButton get() = query("#submit")
    private val QueryAgent.responseLabel get() = query("#response")
    private val ComponentAgent.asTextbox get() = asComp<Textbox>()
    private val ComponentAgent.asLabel get() = asComp<Label>()

    fun testPage(pageUri: String) {
        with(client.connect(pageUri)) {
            assertEquals("", nameInput.asTextbox.value)
            nameInput.input("Tester");
            assertEquals("Tester", nameInput.asTextbox.value)

            submitButton.click();
            assertEquals("Hello Tester!", responseLabel.asLabel.value)
        }
    }
}