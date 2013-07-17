## Handy Cypher Queries
##################################################

## Count of all nodes and relationships
start n = node(*) match n-[r?]->() return count(n), count(r);

## Delete All nodes and relationships
start n = node(*) match n-[r?]->() delete r, n;


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

## Traversal Query:
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


