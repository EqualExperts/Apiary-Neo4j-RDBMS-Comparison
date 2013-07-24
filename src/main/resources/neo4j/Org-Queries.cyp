## Handy Cypher Queries
##################################################

## Count of all nodes and relationships
start n = node(*) match n-[r?]->() return count(distinct(n)), count(distinct(r));

## Delete All nodes and relationships
start n = node(*) match n-[r?]->() delete r, n;


## Delete Node 0
start n = node(0) delete n;

# People at Top Level
###################################################
start n = node(*) where n.level = 1 return id(n) as ID, n.name as Name;

# People with same names
###################################################
start n = node(*)
match n-[r?*]->m
where n.name = m.name
return distinct n.level as Level, n.name as Name
order by Level;

## Measurement Queries
##################################################
## Aggregate Data
## Count of People At all levels
start n = node(*) return n.level as Level, count(n) as Total order by Level;

## Aggregate Traversal Query:
## Find people reporting to a particular person starting at Level 1
## and their reportees (direct and indirect) break up by counts
start n = node:Person(name = "Jennifer Brooks")
match n-[*1]->m-[?*]->o
return m.name as Name, m.level as Level, count(o) as Total
order by Level, Total desc;

## Example at Level 2
start n = node:Person(name = "Andrew Beckford")
match n-[*1]->m-[?*]->o
return m.name as Name, m.level as Level, count(o) as Total
order by Level, Total desc;

## Example at Level 3
start n = node:Person(name = "Andrew Myles")
match n-[*1]->m-[?*]->o
return m.name as Name, m.level as Level, count(o) as Total
order by Level, Total desc;

## Traversal Queries returning names of people that directly or indirectly report
## to a particular person via intermediary bosses.

## Return names of next level reportees
################################################
start n = node:Person(name = "Jennifer Brooks")
match n-[*1]->m-[?*1]->o
return n.name as BigBoss, m.name as Subordinate, m.level as SubordinateLevel, o.name as Reportee, o.level as ReporteeLevel
order by SubordinateLevel, ReporteeLevel;

## Return names of next 2 level reportees
################################################
start n = node:Person(name = "Jennifer Brooks")
match n-[*1]->m-[?*1..2]->o
return n.name as BigBoss, m.name as Subordinate, m.level as SubordinateLevel, o.name as Reportee, o.level as ReporteeLevel
order by SubordinateLevel, ReporteeLevel;

## Return names of next 3 level reportees
################################################
start n = node:Person(name = "Jennifer Brooks")
match n-[*1]->m-[?*1..3]->o
return n.name as BigBoss, m.name as Subordinate, m.level as SubordinateLevel, o.name as Reportee, o.level as ReporteeLevel
order by SubordinateLevel, ReporteeLevel;


## Return names of next 4 level reportees
################################################
start n = node:Person(name = "Jennifer Brooks")
match n-[*1]->m-[?*1..4]->o
return n.name as BigBoss, m.name as Subordinate, m.level as SubordinateLevel, o.name as Reportee, o.level as ReporteeLevel
order by SubordinateLevel, ReporteeLevel;

## Return names of next 5 level reportees
################################################
start n = node:Person(name = "Jennifer Brooks")
match n-[*1]->m-[?*1..5]->o
return n.name as BigBoss, m.name as Subordinate, m.level as SubordinateLevel, o.name as Reportee, o.level as ReporteeLevel
order by SubordinateLevel, ReporteeLevel;

## TODO:
## Hierarchy Lineage
##############################################
start n = node:Person(name = "first37 last222")
match m<-[DIRECTLY_REPORTS_TO*]-n
return m.name as Manager, m.level as ManagerLevel, n.name, n.level
order by ManagerLevel;