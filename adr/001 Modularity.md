Modularity of the application

The most important part of the app is to create proper modularity.
From first glance modularity should be
* geometry that represents basic geometry and vectors
* drawing that represent projection geometry on pixel field
* application that interact with console and orchestrate drawing
* filling the canvas take place on pixel layer, not on geometry.=

All the dependencies should be directed inwside

Geometry <- Drawing <- Application