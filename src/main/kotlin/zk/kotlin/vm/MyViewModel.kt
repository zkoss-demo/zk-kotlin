package zk.kotlin.vm

import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.NotifyChange

class MyViewModel() {
    var name = ""
    val response get() = "Hello $name!"

    @Command("submit")
    @NotifyChange("response")
    fun submit() {
    }
}
