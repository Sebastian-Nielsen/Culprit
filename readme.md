
## How to run

Somehow this works

``
java -cp culprit_2.jar framework.Main
``

but this doesn't:

``
java -jar culprit_2.jar
``

## Important notes

1. when pasting markdown images, set the path to be on the same lvl as 
*content* and *deployment*, so that you avoid having to write the images from *content*
   over into *delpoyment* (which by the way isn't supported). 


### Feature ideas:
1. Expandable/collapsible sections.
   [COLLAPSIBLES]:<> (true|false)
   compiles all:
   ``
   \<!--COLLAPSIBLE(args*)
   <content goes here>
    -->
   ``
   
   An `arg` could be whether it is collapsed initially or not.
   
2. Three sections on a page (left, mid, right) columns. 
    
    Left: 
   
    mid: main content
   
    right: uddybninger af main content. 