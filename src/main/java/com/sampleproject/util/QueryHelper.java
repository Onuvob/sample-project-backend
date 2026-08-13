package com.sampleproject.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@UtilityClass
public class QueryHelper {

    /**
     * Formats a string parameter for SQL LIKE queries with wildcards.
     * Escapes special characters: \ % _
     *
     * @param value the search value
     * @return formatted string with % wildcards, or null if input is blank
     */
    public String formatLikeParam(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String escaped = escapeLikeSpecialChars(value.trim());
        return "%" + escaped + "%";
    }

    /**
     * Formats a string parameter for case-insensitive SQL LIKE queries.
     * Converts to lowercase + adds wildcards + escapes special characters.
     *
     * @param value the search value
     * @return lowercase formatted string with % wildcards, or null if input is blank
     */
    public String formatLikeParamLower(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String escaped = escapeLikeSpecialChars(value.trim().toLowerCase());
        return "%" + escaped + "%";
    }

    /**
     * Escapes special characters for SQL LIKE pattern matching.
     *
     * @param value the input string
     * @return escaped string safe for LIKE queries
     */
    public String escapeLikeSpecialChars(String value) {
        if (value == null) return null;
        return value.replace("\\", "\\\\")   // Escape backslash first
                .replace("%", "\\%")      // Escape percent
                .replace("_", "\\_");     // Escape underscore
    }

    /**
     * Formats parameter for exact match (no wildcards), with escaping.
     *
     * @param value the search value
     * @return escaped string, or null if input is blank
     */
    public String formatExactParam(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return escapeLikeSpecialChars(value);
    }

    public LocalDate parseDateSafe(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            // Log warning and return null (filter will be ignored)
            log.warn("Invalid date format: {}, expected yyyy-MM-dd", dateStr);
            return null;
        }
    }

    /**
     * Converts a LocalDate to a LocalDateTime range for filtering.
     * Returns [startOfDay, startOfNextDay) for inclusive date filtering.
     *
     * @param date the input LocalDate (e.g., from request)
     * @return array of [startDateTime, endDateTime], or [null, null] if input is null
     */
    public LocalDateTime[] toLocalDateTimeRange(LocalDate date) {
        if (date == null) {
            return new LocalDateTime[]{null, null};
        }
        LocalDateTime start = date.atStartOfDay();           // 2024-01-01T00:00
        LocalDateTime end = start.plusDays(1);                // 2024-01-02T00:00 (exclusive)
        return new LocalDateTime[]{start, end};
    }

    /**
     * Convenience method: extract start of day from LocalDate
     */
    public LocalDateTime toStartOfDay(LocalDate date) {
        return (date != null) ? date.atStartOfDay() : null;
    }

    /**
     * Convenience method: extract start of next day from LocalDate
     */
    public LocalDateTime toStartOfNextDay(LocalDate date) {
        return (date != null) ? date.atStartOfDay().plusDays(1) : null;
    }
}
