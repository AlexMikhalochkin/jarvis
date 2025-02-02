package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.connector.megad.RefreshStoredStatesWorker
import com.am.jarvis.connector.megad.StatesRefresher
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [RefreshStoredStatesWorker].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class RefreshStoredStatesWorkerTest {

    @MockK(relaxUnitFun = true)
    private lateinit var refresher: StatesRefresher

    @InjectMockKs
    private lateinit var worker: RefreshStoredStatesWorker

    @Test
    fun testRefresh() {
        worker.refresh()

        verify { refresher.run() }
    }
}
