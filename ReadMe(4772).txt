prp_4772(prp aka programming project)

Kleinberg’s HITS Algorithm and Google’s PageRank algorithm in Java
Bug Report:

1. All the vertices and edges have non negative values in the input text file.
2. The input set of edges and vertices must be exactly equivalent to the count of vertex and edges mentioned in the beginning of the text file.
3. The algorithm throws exception if the runtime arguments 0 and 1 are fractions.

4. File name in argument 3 must contain .txt extension 
5. Tested both algorithms for small and big (20 vertices) graphs on AFS server.

6. Tested for very large graph (vertices > 20).

7. For some of the criteria such as number of arguments to be 3, value of second argument to be greater than equal to -2 and less than equal to 1, number of vertices value (first value in text file) should not be negative.
 The algorithm exits and shows defensive nature.

8. Tested compilation of the algorithm on AFS JAVAC version 1.8.0_144
 after unzipping of the file.