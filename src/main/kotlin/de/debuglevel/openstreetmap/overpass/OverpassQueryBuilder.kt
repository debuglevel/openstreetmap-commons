package de.debuglevel.openstreetmap.overpass

import mu.KotlinLogging
import java.time.Duration

/**
 * Utility functions to generate Overpass query parts
 */
object OverpassQueryBuilder {
    private val logger = KotlinLogging.logger {}

    /**
     * Generate a line to output the results as CSV.
     * [columns] defines the list of columns which should be extracted from the original query output.
     * [header] specifies whether a header row with column names should be printed.
     * [delimiter] separates the CSV columns (the default tabulator is probably a safe choice for OpenStreetMaps data).
     */
    fun csvOutput(columns: List<String>, header: Boolean = true, delimiter: String = "\t"): String {
        logger.trace { "Building CSV output setting..." }

        val columnList = columns.joinToString(", ")
        val setting = "[out:csv($columnList; $header; \"$delimiter\")];"

        logger.trace { "Built CSV output setting: $setting" }
        return setting
    }

    /**
     * Generate a line to ask the server to modify the [timeout].
     * If [timeout] is null, no line is generated and therefore the server default is used.
     */
    fun timeout(timeout: Duration?): String {
        // if timeout is set, it must be non-negative
        require(timeout == null || !timeout.isNegative) { "Timeout must be non-negative or null" }

        logger.trace { "Building timeout setting..." }
        val setting = if (timeout != null) "[timeout:${timeout.seconds}]" else ""
        logger.trace { "Built timeout setting: $setting" }
        return setting
    }
}