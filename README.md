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

| News Article Screen | Favourites Screen | Search Screen |
| :---:         |     :---:      |          :---: |
| ![screenshot_1](https://user-images.githubusercontent.com/75408941/147407538-ba12e069-cc5b-4155-a4b2-6a352312c5ba.png)   | ![screenshot_2](https://user-images.githubusercontent.com/75408941/147407555-354fc17b-52d1-4352-936d-a0d13f3f3cd4.png)     | ![screenshot_3](https://user-images.githubusercontent.com/75408941/147407571-430dba80-1b6c-4117-b988-6efe39e1378e.png)    |
| WebView Screen | Swipe To Delete | Add To Favourites |
| :---:         |     :---:      |          :---: |
| ![1640452710548](https://user-images.githubusercontent.com/75408941/147407549-7bd0420a-86cf-422a-8124-fdc401ec75e4.png) | ![1640452731606](https://user-images.githubusercontent.com/75408941/147407557-0180a048-351a-41bc-a5db-26ac195a2d99.png) | ![1640452962571](https://user-images.githubusercontent.com/75408941/147407567-3de90198-b587-4309-beb0-1f1c251d45ec.png) |
