package zk.kotlin.vm.util

import org.zkoss.bind.BindUtils
import kotlin.reflect.KProperty

fun Any.notifyChange(propName: String) =
        BindUtils.postNotifyChange(null, null, this, propName)

fun Any.notifyChange(prop: KProperty<*>) =
        notifyChange(prop.name) /*calls the method above*/

