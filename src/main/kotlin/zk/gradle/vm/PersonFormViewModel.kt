package zk.gradle.vm

import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zul.ListModelList

const val PERSON_TO_EDIT = "personToEdit"

class PersonFormViewModel {
    val commands = object {
        val edit = "edit"
        val save = "save"
        val cancel = "cancel"
    }

    val persons = ListModelList(listOf(
            Person(name = "Peter", age = 45, address = Address(street = "Nanjing Rd", houseNumber = 123, city = "Taipei")),
            Person(name = "Marvin", age = 30, address = Address(street = "Main St", houseNumber = 30, city = "London")),
            Person(name = "Graham", age = 37, address = Address(street = "Bahnhoftstr.", houseNumber = 12, city = "Berlin"))
    ));

    var personToEdit: Person? = null

    @NotifyChange(PERSON_TO_EDIT)
    fun edit(@BindingParam("person") person: Person) {
        personToEdit = person
    }

    @Command
    @NotifyChange(PERSON_TO_EDIT)
    fun save() {
        persons.notifyChange(personToEdit)
        personToEdit = null
        Clients.showNotification("saved")
    }

    @Command
    @NotifyChange(PERSON_TO_EDIT)
    fun cancel() {
        personToEdit = null
        Clients.showNotification("cancel")
    }
}

@FormBean
data class Person(var name: String, var age: Int, var address: Address)

@FormBean
data class Address(var street: String, var houseNumber: Int, var city: String)
