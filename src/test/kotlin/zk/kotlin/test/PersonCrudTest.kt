package zk.kotlin.test

import org.junit.Assert.*
import org.junit.Test
import org.zkoss.zats.mimic.ComponentAgent
import org.zkoss.zats.mimic.QueryAgent
import org.zkoss.zul.Intbox
import org.zkoss.zul.Label
import org.zkoss.zul.Textbox
import zk.kotlin.test.util.asComp

class PersonCrudTest : BaseTest() {

    private fun connectPersonListPage() = client.connect("/personCrud.zul")

    private val cellsPerRow = 2

    private val QueryAgent.personList get() = query("#personList")
    private fun QueryAgent.personRow(index: Int) = personList.getChild(index * cellsPerRow)
    private val ComponentAgent.editButton get() = nextSibling.query("a[iconSclass=z-icon-edit]")
    private val ComponentAgent.deleteButton get() = nextSibling.query("a[iconSclass=z-icon-times]")

    private val QueryAgent.personEdit get() = query("#personEdit")
    private val QueryAgent.nameInput get() = query("#personName")
    private val QueryAgent.ageInput get() = query("#personAge")
    private val QueryAgent.cancelButton get() = query("button[label=cancel]")
    private val QueryAgent.saveButton get() = query("button[label=save]")
    private val QueryAgent.createButton get() = query("button[iconSclass=z-icon-plus]")

    @Test
    fun testPersonCrudRender() {
        with(connectPersonListPage()) {
            assertEquals(3 * cellsPerRow, personList.children.size)
            assertNull(personEdit)
            assertNotNull(createButton)
        }
    }

    @Test
    fun testPersonCrudCreate() {
        with(connectPersonListPage()) {
            createButton.click()
            assertNotNull(personEdit)
            assertNull(createButton)

            nameInput.input("New Person")
            ageInput.input(77)
            saveButton.click()
            assertNull(personEdit)
            assertNotNull(createButton)
            assertEquals(4 * cellsPerRow, personList.children.size)
            assertEquals("New Person (77)", personRow(3).asComp<Label>().value)
        }
    }

    @Test
    fun testPersonCrudDelete() {
        with(connectPersonListPage()) {
            personRow(2).deleteButton.click()
            assertEquals(2 * cellsPerRow, personList.children.size)
            assertEquals("Peter (45)", personRow(0).asComp<Label>().value)
            assertEquals("Marvin (30)", personRow(1).asComp<Label>().value)

            personRow(0).deleteButton.click()
            assertEquals(1 * cellsPerRow, personList.children.size)
            assertEquals("Marvin (30)", personRow(0).asComp<Label>().value)
        }
    }

    @Test
    fun testPersonCrudEditCancel() {
        with(connectPersonListPage()) {
            personRow(0).editButton.click()
            assertNotNull(personEdit)
            assertNull(createButton)

            personEdit.cancelButton.click()
            assertNull(personEdit)
        }
    }

    @Test
    fun testPersonCrudEditSave() {
        with(connectPersonListPage()) {
            personRow(2).editButton.click()
            assertNotNull(personEdit)
            with(personEdit) {
                assertEquals("Graham", nameInput.asComp<Textbox>().value)
                assertEquals(37, ageInput.asComp<Intbox>().value)

                nameInput.input("Grammy")
                ageInput.input(100)
                saveButton.click()
            }
            assertNull(personEdit)
            assertEquals("Grammy (100)", personRow(2).asComp<Label>().value)
        }
    }
}
