## Handy Cypher Queries
##################################################

## Count of all nodes and relationships
start n = node(*), r = relationship(*)  return count(distinct(n)), count(distinct(r));

## Delete All nodes and relationships
start n = node(*), r= relationship(*)  match p = n-[r?]->() delete p;


## Measurement Queries
##################################################
## Aggregate Data
## Count of People At all levels
start n = node(*) return n.level as Level, count(n) as Total order by Level;

## Traversal Query:
## Find people reporting to a particular person starting at Level 1
## and their break up by counts
start n = node:node_auto_index(name = "Jennifer Brooks")
match n-[*1]->m-[?*]->o
return m.name as Name, m.level as Level, count(o) as Total
order by Level, Total desc;

## Example at Level 2
start n = node:node_auto_index(name = "Andrew Beckford")
match n-[*1]->m-[?*]->o
return m.name as Name, m.level as Level, count(o) as Total
order by Level, Total desc;

## Example at Level 3
start n = node:node_auto_index(name = "Andrew Myles")
match n-[*1]->m-[?*]->o
return m.name as Name, m.level as Level, count(o) as Total
order by Level, Total desc;
