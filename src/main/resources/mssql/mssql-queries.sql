#Handy query

SELECT count(*)
FROM person;

DELETE FROM person_direct_reportee;
DELETE FROM person;


#People at a given level
SELECT level, name 
FROM person 
WHERE level = 1;


#Aggregate query

SELECT level, count(id)
FROM person
GROUP BY level;

#traversal query - count of immediate reportees of people reporting to "first1 last1"
SELECT T.directReportees, sum(T.count) AS totalCount
FROM (
    (SELECT manager.directly_manages AS directReportees, 0 AS count
    FROM person_direct_reportee manager
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1'))
UNION
	(SELECT manager.directly_manages AS directReportees, count(reportee.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee reportee
    ON manager.directly_manages = reportee.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages)
) AS T
GROUP BY T.directReportees;

#traversal query - count till depth 2 reportees of people reporting to "first1 last1"
SELECT T.directReportees, sum(T.count) AS totalCount
FROM (
    SELECT manager.directly_manages AS directReportees, 0 AS count
    FROM person_direct_reportee manager
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
UNION
    SELECT manager.directly_manages AS directReportees, count(reportee.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee reportee
    ON manager.directly_manages = reportee.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth2Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
) AS T
GROUP BY T.directReportees;

#traversal query - count till depth 3 reportees of people reporting to "first1 last1"
SELECT T.directReportees, sum(T.count) AS totalCount
FROM (
    SELECT manager.directly_manages AS directReportees, 0 AS count
    FROM person_direct_reportee manager
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
UNION
    SELECT manager.directly_manages AS directReportees, count(reportee.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee reportee
    ON manager.directly_manages = reportee.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth2Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth3Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
) AS T
GROUP BY T.directReportees;


#traversal query - count till depth 4 reportees of people reporting to "first1 last1"
SELECT T.directReportees, sum(T.count) AS totalCount
FROM (
    SELECT manager.directly_manages AS directReportees, 0 AS count
    FROM person_direct_reportee manager
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
UNION
    SELECT manager.directly_manages AS directReportees, count(reportee.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee reportee
    ON manager.directly_manages = reportee.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth2Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth3Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth4Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
                JOIN person_direct_reportee depth4Reportees
                ON depth3Reportees.directly_manages = depth4Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
) AS T
GROUP BY T.directReportees;

#traversal query - count till depth 5 reportees of people reporting to "first1 last1"
SELECT T.directReportees, sum(T.count) AS totalCount
FROM (
    SELECT manager.directly_manages AS directReportees, 0 AS count
    FROM person_direct_reportee manager
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
UNION
    SELECT manager.directly_manages AS directReportees, count(reportee.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee reportee
    ON manager.directly_manages = reportee.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth2Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth3Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth4Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
                JOIN person_direct_reportee depth4Reportees
                ON depth3Reportees.directly_manages = depth4Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
UNION
    SELECT manager.directly_manages AS directReportees, count(depth5Reportees.directly_manages) AS count
    FROM person_direct_reportee manager 
    JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
                JOIN person_direct_reportee depth4Reportees
                ON depth3Reportees.directly_manages = depth4Reportees.person_id
                    JOIN person_direct_reportee depth5Reportees
                    ON depth4Reportees.directly_manages = depth5Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages
) AS T
GROUP BY T.directReportees;