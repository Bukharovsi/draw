Drawing violating Liskov Substitution Principle

*DESC:*
How violates? 
Drawing module need to know what exactly shape is passed to create a proper wrapper. 
It means if we want to add any shape to geometry we have to add a wrapper to the drawing module. 

why we did it?
But it allows us to segregate responsibility. Geometry has no idea about pixels and
drawing module does not care about basic geometry rules. 