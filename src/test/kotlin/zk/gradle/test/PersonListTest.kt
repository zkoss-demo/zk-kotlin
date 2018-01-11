package zk.gradle.test

import org.junit.*
import org.zkoss.zats.junit.AutoEnvironment
import org.zkoss.zats.mimic.ComponentAgent
import org.zkoss.zats.mimic.DesktopAgent

class PersonListTest {

    companion object {
        //init/destroy a DefaultZatsEnvironment once for the whole test class
        @ClassRule @JvmField
        val env = AutoEnvironment("./src/main/webapp/WEB-INF", "./src/main/webapp")
    }

    //automatically creates/destroys a Zats Client around each @Test method
    @Rule @JvmField
    val client = env.autoClient()

    @Test
    fun testPersonList() {
        val desktop = client.connect("/personList.zul")
        val personList = desktop.query("#personList")
        Assert.assertEquals(3, personList.children.size)

        Assert.assertNull(desktop.personEdit())
        personList.getChild(0).query("a[iconSclass=z-icon-edit]").click()
        val personEdit: ComponentAgent = desktop.personEdit()
        Assert.assertNotNull(personEdit)
        personEdit.query("button[label=cancel]").click()
        Assert.assertNull(desktop.personEdit())
    }

    private fun personEdit(desktopAgent: DesktopAgent) = desktopAgent.query("#personEdit")

}

fun DesktopAgent.personEdit() = query("#personEdit")
