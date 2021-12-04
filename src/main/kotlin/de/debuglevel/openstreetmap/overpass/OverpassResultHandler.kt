package de.debuglevel.openstreetmap.overpass

import de.westnordost.osmapi.common.Handler

/**
 * A [OverpassResultHandler] consumes the result from the Overpass API, parses it and returns the transformed data.
 */
interface OverpassResultHandler<T> : Handler<Array<String>> {
    /**
     * Parse and get the results from the query.
     */
    fun getResults(): List<T>
}