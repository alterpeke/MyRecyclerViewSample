# MyRecyclerViewSample

This app was thinking using widget RecyclerView getting images from URL: https://jsonplaceholder.typicode.com/photos

# First Commit
In this part I just focused to get used the widget and first to show up images loaded manually into drawable folder.
Then I've tested using Picasso library getting the source from a hardcode URL

# Second Commit
Add the rest connection getting resources from defined URL. Work with picasso library to render the pictures into viewholder (handler of Recyclerview)

### Solution Desing (Java Classes)
#### Activity Folder
##### MainActivity
The one activity that handle the recyclerview widget and load the images
##### CreateList
Object created to handle the images URL into the ViewHolder
##### MyAdapter
RecyclerView adapter which create and bind the images into the cell Layout

#### Utils Folder
##### AsyncConnector
Java class which implements AsyncTask to do the REST connection.
##### Callback
Interface to perform callback 
##### DataHandler
Class where it defines the object attributes 

### Layout (XML Resources)
##### activity_main.xml
Layout where the recyclerview is set up

##### cell_layout.xml
Layout resource used to set the image and the title (if you decide to use it) of image displayed into recyclerview

### Improvements possibilities
##### To check the sort of connection and evaluate if it's available
##### Progressbar into RecyclerView
##### Dynamic value to set the number of columns in recyclerview

