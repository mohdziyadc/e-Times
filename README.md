# e-Times

This a news app which follows MVVM architecture. 

## Key Features

 - UI - Jetpack Compose
    - LazyColumn
    - Compose Navigation
    - Light/Dark Theme
    - Material Design
 - The RESTful API used is News API.
 - Retrofit for networking.
 - Android Room for data persistence : ArticleDAO, NewsDatabase, Converters, Article Entity are used.
 - Kotlin Coroutines & Flows: Coroutines are used for network calls and Flows are used to fetch the data from the database.
 - Pagination: Paging 3 library is used for pagination.
 - MVVM Architecture: NewsViewModel for business logic. NewsRepository for accessing data.
 - Searching : Retrieves search results from the REST API.
 - WebView.
 - Swipe to Delete.

| Home Screen #1 | Home Screen #2 | Search Screen |
| :---:         |     :---:      |          :---: |
| ![screenshot_1](https://user-images.githubusercontent.com/75408941/147407538-ba12e069-cc5b-4155-a4b2-6a352312c5ba.png)   | ![screenshot_2](https://user-images.githubusercontent.com/75408941/147407549-7bd0420a-86cf-422a-8124-fdc401ec75e4.png)     | ![screenshot_3](https://user-images.githubusercontent.com/75408941/147407555-354fc17b-52d1-4352-936d-a0d13f3f3cd4.png)    |
