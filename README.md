# RockStars App

The project follows a simplified for of MVI architecturarl patten

### Intent

An Intent represents an action performed by the user. For example let's say the user Clicks  a rockStart to see the details, that will be `RockStarClickedIntent` . All Intents from a View are grouped (or merged) and sent to the viewModel through the same door (method)

### State

The `State` is actually what everything is all about. Once the Result of an operation is produced, the ViewModel will try to perform a form of **State Management System** in order to produce a new `State`. The State we are talking about here is basically a `ViewState`. This means that we are trying to represent what the end user sees on the screen in a structured way. 
So basically everything will start with a State and finish with anther State.

### View

The View also keep it's usual role. The View will still have a reference to the ViewModel and will have one important function called `render()`. This function will be called each time a new State change is observed and will be responsible for rendering the new State (ViewState) to the Screen. 

The View will also call the ViewModel only time to provide Intents as a stream.

### The Contract

A `Contract` is defined in the presentation layer to establish the terms of communication between the ViewModel and View. 
That contract will contract de declaration of ViewStates and Intents 

Everything will be done according to this contract

### Dependency Injection and Dependency Inversion

We made sure to follow some basic SOLID principles. The one mostly used is the Dependency Inversion principle. And we use Koin as Dependency Injection framework to help implement de Dependency Inversion principle without much effort.

### Memory Cache

We've a built a very basic Memory Cache system to hold data for each session. We attempt to use that memory cache as **Single Source of Truth** (SSoT).  That means we only load data from network if the memory cache is empty. The memory cache is based on a simple HashMap 

### Preferences

We store the Favorites in Android Shared Preference system and take advantage of b**asic custom Serializer class** in order to only work with strings

### Experimental Tools

- Moshi (first time using it)
- Coil Image loader (first time using it)