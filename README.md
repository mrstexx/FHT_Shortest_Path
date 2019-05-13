## Implementation of Priority First Search Algorithms
Final project for subject Algorithms and Data Structures (Summer Semester 2019) at University of Applied Sciences Technikum in Vienna.

## Exercise?
It is possible to import city public transport network structure (including metros and tramways) and from these data to create graph. 
Once we have transport network as graph created, we want to find shortest way between given *start* and *end* station. 
Output should be time amount and path (list of stations per different lines).

Between each station, it is also given cost (time in minutes), but we also need to consider that in case of changing lines, it can cost us some time (e.g 5 minutes).

## Solution 
Solution will be using Dijkstra Algorithm and heap (better performance) for getting shortest path.

## Optional feature
The focus is to have runnable, optimized and completed solution. Optional would be to have options
to visual vertices (as circles) and edges (relations between vertices) in GUI program, and option
to select final path.