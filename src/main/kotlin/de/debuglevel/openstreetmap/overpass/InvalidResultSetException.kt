package de.debuglevel.openstreetmap.overpass

/**
 * [InvalidResultSetException] is thrown if the ResultSet does not seem to be correct;
 * e.g. if a CSV header is not present, which should even be present if the ResultSet is empty.
 */
class InvalidResultSetException : Exception("Received ResultSet is invalid")
