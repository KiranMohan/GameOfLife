GameOfLife
==========

Implementation Conway's_Game_of_Life http://en.wikipedia.org/wiki/Conway's_Game_of_Life
------------------------------------

The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

Any live cell with less than two live neighbours dies, as if caused by under-population.  
Any live cell with two or three live neighbours lives on to the next generation.  
Any live cell with more than three live neighbours dies, as if by overcrowding.  
Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.  

The initial pattern constitutes the seed of the system. The first generation is created by applying the above rules simultaneously to every cell in the seed—births and deaths occur simultaneously, and the discrete moment at which this happens is sometimes called a tick (in other words, each generation is a pure function of the preceding one). The rules continue to be applied repeatedly to create further generations.

Steps to Run (from Eclipse)
------------
1. Import as an Eclipse project.
2. If you want a graphical display of the results, add the library http://algs4.cs.princeton.edu/code/stdlib.jar to class path.
3. Run the file GameOfLife.java
