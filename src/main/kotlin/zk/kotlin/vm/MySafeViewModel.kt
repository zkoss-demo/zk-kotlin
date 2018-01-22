package zk.kotlin.vm

import org.zkoss.bind.annotation.Command
import zk.kotlin.vm.util.notifyChange

class MySafeViewModel() {
    val SUBMIT = ::submit.name;
    var name = ""
    val response get() = "Hello $name!"

    @Command fun submit() {
        notifyChange(::response.name)
    }
}
