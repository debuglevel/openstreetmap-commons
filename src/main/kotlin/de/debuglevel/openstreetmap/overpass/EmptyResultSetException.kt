package de.debuglevel.openstreetmap.overpass

/**
 * [EmptyResultSetException] is thrown if there are no results in the ResultSet; i.e. it is empty.
 */
class EmptyResultSetException : Exception("Received ResultSet is empty")
