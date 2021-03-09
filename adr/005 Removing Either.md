Removing Either

Either looks awesome for typical business logic, when some business logic might handle exceptions and 
and process scenario with some default values or so on.
But usage pattern is a little different here. All the exceptions are handled on application layer.
And therefore Either requires us to handle `left` a lot, when it is not necessery.
In order to simplify solution we decided to remove either.