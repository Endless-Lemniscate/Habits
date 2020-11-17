# Habits
Android application for tracking habits. 
This is a sample android Clean Architecture app written in Kotlin

## Statement of Work
Write application with 2 activities.
**First activity** consists of habits list. Each habit has following fields:

* name
* description
* priority
* type
* periodicity
* color

First time you open the app displays no habits. Also this activity should have button which opens another activity.

**Second activity** - screen of create/edit habit - has following fields:

* name
* description
* priority (Spinner element)
* type of habit (Radio button element)
* two fields for putting periodicity and execution amount

This activity should have "save" button, which opens activity with list of habits. Clicking on any list element should open edit activity.

The project must visually match the material design.
 
In addition:
On the create/edit screen add color picker element, which should consist of two parts:

* Element for choosing color
* Element displaying which color is chosen

**Element for choosing color:**
Horizontal scrollView element in which situated 16 squares with same distance between them.

1. Each square should have margin not less than 25% of its size
2. Each square should have its own color
3. Portrait oriented screen holds 3 or 4 squares. It should be possible to get another color by scrolling
User should can choose color by clicking on correspondent square.
4. ScrollView element should have HUE scale gradient background
5. Color of each square same as color of background behind central point of square

**Element displaying chosen color:**
Rectangle with background of chosen color. Also it should have HSV and RGB strings on it

***

### Addition 1
* Change all activities to fragments (make app with single activity)
* Add navigation drawer element with "home screen" and "about application" pages
* Add navigation controller to project

### Addition 2
* Change all activities to fragments (make app with single activity)
* Add navigation drawer element with "home screen" and "about application" pages
* Add navigation controller to project

### Addition 3
* Create two AndroidX ViewModels for list screen and create/edit screen
* Add bottomSheet element to list screen for sort and search list items. This bottomSheet should use same viewModel as list fragment

### Addition 4
* Create Room database for application
* Store habits instances in it
* Use liveData for receiving data from database

### Addition 5
Use coroutines for "insert to database" function

### Addition 6
Fetch data from remote server and vice versa

### Addition 7
Transit project to clean architecture
Create 3 modules:

1. domain
2. data 
3. presentation

Use dagger for DI

**Implement following business logic:**
On each element in list add button "execute". Clicking this button performs following actions:

* **For bad habits:** if this habit was executed less than allowed in certain period then display Toast element with text 
"You can do it $some_number times", but if more then "Stop doing this"

* **For good ones:** if this habit was executed less than needed in certain period then display Toast element with text 
"You should do it $some_number times", but if more then "You are breathtaking!"

### Addition 8
Write Unit tests for:

* one ViewModel
* all UseCases
* repository

Write UI test for any screen