package zk.kotlin.test

import org.junit.Assert.*
import org.junit.Test
import org.zkoss.zats.mimic.ComponentAgent
import org.zkoss.zats.mimic.QueryAgent
import org.zkoss.zul.Intbox
import org.zkoss.zul.Label
import org.zkoss.zul.Textbox
import zk.kotlin.test.util.asComp

class PersonListTest : BaseTest() {

    private fun connectPersonListPage() = client.connect("/personList.zul")

    private val QueryAgent.personList get() = query("#personList")
    private val QueryAgent.editButton get() = query("a[iconSclass=z-icon-edit]")
    private val QueryAgent.personListLabel get() = editButton.nextSibling

    private val QueryAgent.personEdit get() = this.query("#personEdit")
    private val QueryAgent.nameInput get() = query("#personName")
    private val QueryAgent.ageInput get() = query("#personAge")
    private val QueryAgent.cancelButton get() = query("button[label=cancel]")
    private val QueryAgent.saveButton get() = query("button[label=save]")

    @Test
    fun testPersonListRender() {
        with(connectPersonListPage()) {
            assertEquals(3, personList.children.size)
            assertNull(personEdit)
        }
    }

    @Test
    fun testPersonListEditCancel() {
        with(connectPersonListPage()) {
            personList.firstChild.editButton.click()
            assertNotNull(personEdit)

            personEdit.cancelButton.click()
            assertNull(query("#personEdit"))
        }
    }

    @Test
    fun testPersonListEditSave() {
        with(connectPersonListPage()) {
            personList.lastChild.editButton.click()
            assertNotNull(personEdit)
            with(personEdit) {
                assertEquals("Graham", nameInput.asComp<Textbox>().value)
                assertEquals(37, ageInput.asComp<Intbox>().value)

                this.nameInput.input("Grammy")
                ageInput.input(100)
                saveButton.click()
            }
            assertNull(personEdit)
            assertEquals(
                    "Person(name=Grammy, age=100, address=Address(street=Bahnhofstr., houseNumber=12, city=Berlin))",
                    personList.lastChild.personListLabel.asComp<Label>().value)
        }
    }
}
