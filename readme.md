
## To generate jar file

1. run the `shadowJar` gradle task
2. Find the generated .jar in `build/libs/culprit_2-all.jar`



### Set up 'File Watcher'

Set the arguments to: `$ContentRoot$ --single $FilePath$`



## How to run

``
java -jar culprit_2.jar
``


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
   
3. Support multiple spaces in .md 

4. Should support image paste