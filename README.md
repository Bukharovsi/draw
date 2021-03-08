## Technical Overview
### Modularity

There are 3 modules
 - Geometry
 - Drawing
 - Application

**Geometry** is responsible for all the vector shapes and knows how to represent any 
shape in the Cartesian coordinate system. Actually it means that geometry represents
vectors and does not care about pixel representation

**Drawing** is about pixels and how to represent any vector shape on a pixel field
There are 3 packages
 - **pixel**  contains pixel itself and array of pixel on a surface 
 - **drawable** knows how to draw any vector shape on a pixel layer
 - **filling** there is  a requirement to fill an area. But it is not a vector 
operation and must be performed on rasterized field
Canvas just an aggregation root (or endpoint) for all operations that allows 
   an application to manipulate with a picture
   
**Application**
Represents the application itself and contains of
 - **commands** that represent some actions that manipulate the picture. E.x.
Add line, fill area and so on
 - **pixel** represent a border for pixel layer. it is not a part of domain language
and exists only because of the application. Therefore it is inside application package
   
###Dependencies
Geometry <- Drawable <- Application

It means that Geometry is domain core of the application and does not aware neighbours.
Drawable is also part of domain core, but it is aware of geometry, but does not aware 
of application

## Architecture decision record
The track of ADRs might be found inside `adr` folder 

## How to build & run
to compile it you need JDK at least 1.8. But also tested on JDK 12.

Build - `./gradlew build`

Run - `./gradlew shadowJar && java -jar build/libs/drawing-all.jar`

## The task

You're given the task of writing a simple console version of a drawing program.
At this time, the functionality of the program is quite limited but this might change in the future.
In a nutshell, the program should work as follows:
1. Create a new canvas
2. Start drawing on the canvas by issuing various commands
3. Quit


Command 		Description

`C w h`             Should create a new canvas of width w and height h.

`L x1 y1 x2 y2`     Should create a new line from (x1,y1) to (x2,y2). Currently only
                    horizontal or vertical lines are supported. Horizontal and      
                    vertical lines will be drawn using the 'x' character.

`R x1 y1 x2 y2`     Should create a new rectangle, whose upper left corner is (x1,y1) 
                    and lower right corner is (x2,y2). Horizontal and vertical lines 
                    will be drawn using the 'x' character.

`B x y c`           Should fill the entire area connected to (x,y) with "colour" c. The
                    behavior of this is the same as that of the "bucket fill" tool in 
                    paint programs.

`Q`                 Should quit the program.

__Sample I/O__

Below is a sample run of the program. User input is prefixed with enter command:

```
enter command: C 20 4

----------------------
|                    |
|                    |
|                    |
|                    |
----------------------
```
```
enter command: L 1 2 6 2
----------------------
|                    |
|xxxxxx              |
|                    |
|                    |
----------------------
```
```
enter command: L 6 3 6 4
----------------------
|                    |
|xxxxxx              |
|     x              |
|     x              |
----------------------
```
```
enter command: R 14 1 18 3
----------------------
|             xxxxx  |
|xxxxxx       x   x  |
|     x       xxxxx  |
|     x              |
----------------------
```
```
enter command: B 10 3 o
----------------------
|oooooooooooooxxxxxoo|
|xxxxxxooooooox   xoo|
|     xoooooooxxxxxoo|
|     xoooooooooooooo|
----------------------
```
```
enter command: Q
```