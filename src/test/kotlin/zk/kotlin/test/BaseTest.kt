package zk.kotlin.test

import org.junit.ClassRule
import org.junit.Rule
import org.zkoss.zats.junit.AutoEnvironment

open class BaseTest {
    companion object {
        @ClassRule @JvmField
        val env = AutoEnvironment("./src/main/webapp/WEB-INF", "./src/main/webapp")
    }

    @Rule @JvmField
    val client = env.autoClient()
}