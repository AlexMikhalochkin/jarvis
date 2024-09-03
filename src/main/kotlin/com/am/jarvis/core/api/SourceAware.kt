package com.am.jarvis.core.api

/**
 * Interface for components that are aware of the source of the smart devices.
 *
 * This interface defines a contract for components that are aware of the source of the smart devices.
 * Implementations of this interface should provide the logic to retrieve the source of the smart devices.
 *
 * @author Alex Mikhalochkin
 */
interface SourceAware {

    /**
     * Retrieves the source of the smart devices.
     *
     * @return a string representing the source of the smart devices.
     */
    fun getSource(): String
}
