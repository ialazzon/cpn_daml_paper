import networkx as nx
import re
import pydot

# graph = nx.Graph(nx.nx_pydot.read_dot("StateSpace_cpn_1.dot")).to_directed()
(pydot_graph,) = pydot.graph_from_dot_file("StateSpace_cpn_1.dot")
graph = nx.DiGraph(nx.drawing.nx_pydot.from_pydot(pydot_graph))

start_node = "N1"
end_node = "N17"

all_paths = []

# paths = list(nx.all_simple_paths(graph, source=start_node, target=end_node))

# for path in paths:
#     custom_string = " -> ".join(path)  # Customize your output format here
#     print(custom_string)

all_paths = list(nx.all_simple_paths(graph, source=start_node, target=end_node))
print(len(all_paths))
# print(graph.is_directed())
# print(type(graph))
# print('has edge:',graph.has_edge('N3', 'N1'))

def find_independent_paths(graph, start, end, visited=None, path=None):
    global all_paths
    if visited is None:
        visited = set()
    if path is None:
        path = []

    # Mark the current node as visited and store in path
    visited.add(start)
    path.append(start)

    # If we've reached the end node, print the path
    if start == end:
        # print(" -> ".join(path)) 
        all_paths.append(path[:])
    else:
        # Iterate through all neighboring nodes
        for neighbor in graph.neighbors(start):
            if neighbor not in visited:  # Ensure no node is revisited
                find_independent_paths(graph, neighbor, end, visited, path)

    # Backtrack: remove the current node from path and mark it unvisited
    path.pop()
    visited.remove(start)


# find_independent_paths(graph, start_node, end_node)

pattern = re.compile(r'\w+:(\w+) (\{.*\})')
                 

def print_path(path=[]):
    path_print = ""
    for i in range(len(path)-1):
        # print(graph.edges[path[i], path[i+1]])
        label = graph.edges[path[i], path[i+1]].get('label')
        # print(label)
        match = pattern.search(label)
        # print(match.group(1))
        # print(match.group(2))
        if match.group(1) in ['Iou','Transfer']:
            path_print = path_print + match.group(1) +' ' + match.group(2)+'\n'
    print(" -> ".join(path))
    print(path_print)




for path in all_paths:
    # print(path)
    print_path(path)
