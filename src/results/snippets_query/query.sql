SELECT
    STRING_AGG(CAST(locs AS NVARCHAR(MAX)), ',') AS 'values',
    COUNT(locs) AS count,
    AVG(locs) AS mean,
    STDEV(locs) AS std,
    MIN(locs) AS min,
    MIN(q1) as q1,
    MIN(q2) as q2,
    MIN(q3) as q3,
    MAX(locs) AS max
FROM (
    SELECT
        locs,
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY locs) OVER () AS q1,
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY locs) OVER () AS q2,
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY locs) OVER () AS q3,
        PERCENTILE_CONT(1.0) WITHIN GROUP (ORDER BY locs) OVER () AS q4
    FROM (
        SELECT
            Id,
            LEN(TRIM(CHAR(10) FROM Snippet)) - LEN(REPLACE(TRIM(CHAR(10) FROM Snippet), CHAR(10), '')) + 1 AS locs
        FROM (
            SELECT
                Id,
                CAST(CAST(Body AS XML).query ('pre/code/text()') AS NVARCHAR (MAX)) AS Snippet
            FROM Posts
            WHERE
                Id IN (
                    SELECT AcceptedAnswerId
                    FROM Posts
                    WHERE Tags LIKE '%<##Language?java##>%'
                ) AND
                Body LIKE '%<pre><code>%' AND
                TRY_CAST (Body AS XML) IS NOT NULL
            ) AS Snippets
        ) AS Locs
    ) AS Quartiles
