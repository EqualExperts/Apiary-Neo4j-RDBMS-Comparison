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

#traversal query - count till depth 6 reportees of people reporting to "first1 last1"
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
UNION
    SELECT manager.directly_manages AS directReportees, count(depth6Reportees.directly_manages) AS count
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
						JOIN person_direct_reportee depth6Reportees
						ON depth5Reportees.directly_manages = depth6Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1')
    GROUP BY manager.directly_manages    
) AS T
GROUP BY T.directReportees;

#traversal query - details of reportees till depth 6 reporting to "first1 last1"
SELECT *
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
						JOIN person_direct_reportee depth6Reportees
						ON depth5Reportees.directly_manages = depth6Reportees.person_id
    WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');
							   
##############################################################################							   
#For measurement purposes:
	#1. Current level is assumed to be always 1 
	#2. Total levels can vary
	#3. Visibility level will vary							   
##############################################################################
## Traversal Queries returning names of people that directly or indirectly report
## to a particular person via intermediary bosses.
##############################################################################

#Visibility level = 1
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate
	FROM person_direct_reportee manager
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');  	

#Visibility level = 2							   
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate, depth1Reportees.directly_manages AS Reportee
	FROM person_direct_reportee manager
	LEFT JOIN person_direct_reportee depth1Reportees
	ON manager.directly_manages = depth1Reportees.person_id  
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');

#Visibility level = 3							   
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate, depth1Reportees.directly_manages AS Reportee1,
	depth2Reportees.directly_manages AS Reportee2
	FROM person_direct_reportee manager 
    LEFT JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        LEFT JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id  
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');
							   
#Visibility level = 4							   
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate, depth1Reportees.directly_manages AS Reportee1,
	depth2Reportees.directly_manages AS Reportee2, depth3Reportees.directly_manages AS Reportee3
	FROM person_direct_reportee manager 
    LEFT JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        LEFT JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            LEFT JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id  
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');
							   
#Visibility level = 5							   
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate, depth1Reportees.directly_manages AS Reportee1,
	depth2Reportees.directly_manages AS Reportee2, depth3Reportees.directly_manages AS Reportee3,
	depth4Reportees.directly_manages AS Reportee4
	FROM person_direct_reportee manager 
    LEFT JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        LEFT JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            LEFT JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
                LEFT JOIN person_direct_reportee depth4Reportees
                ON depth3Reportees.directly_manages = depth4Reportees.person_id  
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');

#Visibility level = 6							   
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate, depth1Reportees.directly_manages AS Reportee1,
	depth2Reportees.directly_manages AS Reportee2, depth3Reportees.directly_manages AS Reportee3,
	depth4Reportees.directly_manages AS Reportee4, depth5Reportees.directly_manages AS Reportee5
	FROM person_direct_reportee manager 
    LEFT JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        LEFT JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            LEFT JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
                LEFT JOIN person_direct_reportee depth4Reportees
                ON depth3Reportees.directly_manages = depth4Reportees.person_id
                    LEFT JOIN person_direct_reportee depth5Reportees
                    ON depth4Reportees.directly_manages = depth5Reportees.person_id  
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');
							   
#Visibility level = 7							   
	SELECT manager.person_id AS Bigboss, manager.directly_manages AS Subordinate, depth1Reportees.directly_manages AS Reportee1,
	depth2Reportees.directly_manages AS Reportee2, depth3Reportees.directly_manages AS Reportee3, depth4Reportees.directly_manages AS Reportee4,
	depth5Reportees.directly_manages AS Reportee5, depth6Reportees.directly_manages AS Reportee6
	FROM person_direct_reportee manager 
    LEFT JOIN person_direct_reportee depth1Reportees
    ON manager.directly_manages = depth1Reportees.person_id
        LEFT JOIN person_direct_reportee depth2Reportees
        ON depth1Reportees.directly_manages = depth2Reportees.person_id
            LEFT JOIN person_direct_reportee depth3Reportees
            ON depth2Reportees.directly_manages = depth3Reportees.person_id
                LEFT JOIN person_direct_reportee depth4Reportees
                ON depth3Reportees.directly_manages = depth4Reportees.person_id
                    LEFT JOIN person_direct_reportee depth5Reportees
                    ON depth4Reportees.directly_manages = depth5Reportees.person_id
                        LEFT JOIN person_direct_reportee depth6Reportees
                        ON depth5Reportees.directly_manages = depth6Reportees.person_id  
	WHERE manager.person_id = (SELECT id
                               FROM person
                               WHERE name = 'first1 last1');