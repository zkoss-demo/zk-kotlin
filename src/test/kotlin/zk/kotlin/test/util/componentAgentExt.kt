package zk.kotlin.test.util

import org.zkoss.zats.mimic.ComponentAgent
import org.zkoss.zats.mimic.operation.OperationAgent
import org.zkoss.zk.ui.Component

inline fun <reified T : Component> ComponentAgent.asComp(): T = this.`as`(T::class.java)
inline fun <reified T : OperationAgent> ComponentAgent.asAgent(): T = this.`as`(T::class.java)
