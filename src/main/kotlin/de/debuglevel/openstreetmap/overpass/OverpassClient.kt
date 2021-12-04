package de.debuglevel.openstreetmap.overpass

import de.westnordost.osmapi.OsmConnection
import de.westnordost.osmapi.overpass.OverpassMapDataDao
import mu.KotlinLogging
import java.time.Duration
import kotlin.system.measureTimeMillis

class OverpassClient(
    baseUrl: String,
    clientTimeout: Duration,
    userAgent: String,
) {
    private val logger = KotlinLogging.logger {}

    private val overpass: OverpassMapDataDao

    init {
        logger.debug { "Initializing..." }

        val millisecondTimeout = clientTimeout.seconds.toInt() * 1000

        logger.trace { "Creating OsmConnection with baseUrl=$baseUrl, userAgent=$userAgent, timeout=$millisecondTimeout..." }
        val osmConnection = OsmConnection(baseUrl, userAgent, null, millisecondTimeout)

        logger.trace { "Creating OverpassMapDataDao..." }
        overpass = OverpassMapDataDao(osmConnection)

        logger.debug { "Initialized" }
    }

    /**
     * Execute [query] and pass results to [overpassResultHandler] to parse it.
     * [serverTimeout] is an assumption on the server-side timeout setting to throw the correct Exception if a query might have taken too long.
     */
    fun <T> execute(
        query: String,
        overpassResultHandler: OverpassResultHandler<T>,
        serverTimeout: Duration
    ): List<T> {
        logger.debug { "Executing query..." }
        logger.trace { "Executing query:\n$query" }

        val queryDurationMillis = measureTimeMillis {
            overpass.queryTable(query, overpassResultHandler)
        }
        val queryDuration = Duration.ofMillis(queryDurationMillis)
        logger.debug { "Query execution took a round trip time of about $queryDuration" } // includes overhead for transfer, parsing et cetera

        val results = try {
            overpassResultHandler.getResults()
        } catch (e: EmptyResultSetException) {
            // if query duration took longer than the server timeout,
            // there is good chance the server timeout was hit
            if (queryDuration >= serverTimeout) {
                throw TimeoutExceededException(serverTimeout, queryDuration)
            } else {
                throw e
            }
        }

        // TODO: possible failures:
        //        - quota reached (what do then?)
        //        - invalid resultset (don't know if and when happens)

        logger.debug { "Executed query: ${results.count()} results." }
        return results
    }

    data class TimeoutExceededException(val serverTimeout: Duration, val queryDuration: Duration) :
        Exception("Query ($queryDuration) exceeded server timeout ($serverTimeout)")
}