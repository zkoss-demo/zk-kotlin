package zk.gradle.vm

import org.zkoss.bind.BindUtils
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zul.ListModelList
import kotlin.reflect.KProperty

class PersonListViewModel {
    val commands = object {
        val EDIT = ::edit.name
        val SAVE = ::save.name
        val CANCEL = ::cancel.name
    }

    val persons = ListModelList(listOf(
            Person(name = "Peter", age = 45, address = Address(street = "Nanjing Rd", houseNumber = 123, city = "Taipei")),
            Person(name = "Marvin", age = 30, address = Address(street = "Main St", houseNumber = 30, city = "London")),
            Person(name = "Graham", age = 37, address = Address(street = "Bahnhofstr.", houseNumber = 12, city = "Berlin"))
    ));

    var personToEdit: Person? = null

    @Command
    fun edit(@BindingParam("person") person: Person) {
        personToEdit = person
        notifyChange(::personToEdit)
    }

    @Command
    fun save() {
        persons.notifyChange(personToEdit)
        personToEdit = null
        notifyChange(::personToEdit)
        Clients.showNotification("saved")
    }

    @Command
    fun cancel() {
        personToEdit = null
        notifyChange(::personToEdit)
        Clients.showNotification("cancel")
    }
}

fun Any.notifyChange(propName: String) = BindUtils.postNotifyChange(null, null, this, propName)
fun Any.notifyChange(prop: KProperty<*>) = BindUtils.postNotifyChange(null, null, this, prop.name)

@FormBean data class Person(var name: String, var age: Int, var address: Address)

@FormBean data class Address(var street: String, var houseNumber: Int, var city: String)
