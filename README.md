# Marvel Comics Android Application

Android application that allows users to display Marvel comics.

Data is fetched from Marvel API: https://developer.marvel.com

Users are allowed to list all comics at main page and search for comics at another page.

Results are displayed on a list. Users are informed if there are no results for the search.

Application displays buttons to retry when error occoured while loading.

User can click on the result item and be redirected to the details page that contains a scrollable description. Details page also allows users to click on "Find out more" button to see more details of comic book on the webstite.

# Used tools and technologies:

- Android Studio
- Android SDK
- MVVM Architecture Design Pattern
- Retrofit 2 - REST Client
- Hilt - Dependency Injection
- RecyclerView - Display data on the list
- Paging 3 - Load paginated data into RecyclerView list
- Glide 4 - Loading images
- ViewPager 2 - Swipe between fragments
- Navigation Component - Navigating between fragments
- Parcelable - Send objects between fragments
- Bottom Sheet - Display scrollable description on Details page

# Screenshots:

![marvel_comics_screenshots](https://user-images.githubusercontent.com/23174038/135831178-319104f2-277b-423e-ae7d-20300ce0d310.png)
