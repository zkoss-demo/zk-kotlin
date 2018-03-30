package zk.kotlin.vm

import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zul.ListModelList
import zk.kotlin.vm.util.notifyChange

@FormBean data class Person(var name: String = "", var age: Int = 0, var address: Address = Address())

@FormBean data class Address(var street: String = "", var houseNumber: Int = 0, var city: String = "")

class PersonCrudViewModel {
    val commands = object {
        val CREATE = ::create.name
        val DELETE = ::delete.name
        val EDIT = ::edit.name
        val SAVE = ::save.name
        val CANCEL = ::cancel.name
    }

    // imagine this being loaded from a DB ... not the topic here
    val persons = ListModelList(listOf(
            Person(name = "Peter", age = 45, address = Address(street = "Nanjing Rd", houseNumber = 123, city = "Taipei")),
            Person(name = "Marvin", age = 30, address = Address(street = "Main St", houseNumber = 30, city = "London")),
            Person(name = "Graham", age = 37, address = Address(street = "Bahnhofstr.", houseNumber = 12, city = "Berlin"))
    ));

    var personToEdit: Person? = null
        set(value) {
            field = value
            notifyChange(::editing)
            notifyChange(::personToEdit)
        }

    val editing get() = personToEdit != null

    @Command
    fun delete(@BindingParam("person") person: Person) {
        persons.remove(person)
        Clients.showNotification("Deleted: ${person.name}")
    }

    @Command
    fun edit(@BindingParam("person") person: Person) {
        personToEdit = person
    }

    @Command
    fun create() {
        personToEdit = Person()
    }

    @Command
    fun save() {
        if(!persons.contains(personToEdit)) {
            persons.add(personToEdit)
        } else {
            persons.notifyChange(personToEdit)
        }
        Clients.showNotification("Saved: ${personToEdit?.name}")
        personToEdit = null
    }

    @Command
    fun cancel() {
        Clients.showNotification("Canceled: ${personToEdit?.name}")
        personToEdit = null
    }
}
